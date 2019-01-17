package pl.reconizer.cityadventure.presentation.map.game

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import com.nhaarman.mockitokotlin2.*
import com.winterbe.expekt.expect
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.mockito.ArgumentMatchers.anyDouble
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.entities.AdventurePoint
import pl.reconizer.cityadventure.domain.entities.Position
import pl.reconizer.cityadventure.domain.repositories.IAdventureRepository
import pl.reconizer.cityadventure.presentation.errorhandlers.ErrorHandler
import pl.reconizer.cityadventure.presentation.location.ILocationProvider
import pl.reconizer.cityadventure.presentation.map.CameraDetails
import pl.reconizer.cityadventure.presentation.map.MapMode
import java.util.concurrent.TimeUnit

class GameMapPresenterSpec : Spek({

    describe("GameMapPresenter") {
        lateinit var testScheduler: TestScheduler
        lateinit var simplePresenter: GameMapPresenter
        lateinit var adventureRepository: IAdventureRepository
        lateinit var errorHandler: ErrorHandler<Error>

        val locationProvider = mock<ILocationProvider>()

        val view = mock<IGameMapView>()

        val location = mock<Location>()
        val position = Position(1.0, 1.0)

        before {
            whenever(location.latitude).thenReturn(position.lat)
            whenever(location.longitude).thenReturn(position.lng)
        }

        beforeEachTest {
            errorHandler = mock()
            adventureRepository =  mock()
            testScheduler = TestScheduler()
            simplePresenter = GameMapPresenter(
                    Schedulers.trampoline(),
                    Schedulers.trampoline(),
                    locationProvider,
                    adventureRepository,
                    errorHandler
            )
        }

        describe("lastLocation") {

            context("when position not available") {
                before {
                    whenever(locationProvider.lastLocation).thenReturn(null)
                }

                it("returns null") {
                    expect(simplePresenter.lastLocation).to.be.`null`
                }
            }

            context("when position is available") {
                val location = mock<Location>()
                val position = Position(1.0, 1.0)

                before {
                    whenever(location.latitude).thenReturn(position.lat)
                    whenever(location.longitude).thenReturn(position.lng)
                    whenever(locationProvider.lastLocation).thenReturn(location)
                }

                it("returns position") {
                    expect(simplePresenter.lastLocation).to.be.equal(position)
                }
            }
        }

        describe("subscribe") {

            it("sets view for error handler") {
                simplePresenter.subscribe(view)
                verify(errorHandler, atLeastOnce()).view = any()
            }

            context("when location permissions are not granted") {
                before {
                    whenever(locationProvider.hasPermission).thenReturn(false)
                }

                it("notifies view about lack of permissions") {
                    simplePresenter.subscribe(view)
                    verify(view, atLeastOnce()).requestLocationPermission()
                }
            }

            context("when location permissions are granted") {
                lateinit var customLocationProvider: MockLocationProvider
                lateinit var presenter: GameMapPresenter

                beforeEachTest {
                    reset(view)
                    customLocationProvider = spy(MockLocationProvider())
                    presenter = GameMapPresenter(
                            testScheduler,
                            testScheduler,
                            customLocationProvider,
                            adventureRepository,
                            errorHandler
                    )
                }

                afterEachTest { presenter.unsubscribe() }

                it("enables location provider") {
                    presenter.subscribe(view, MapMode.ADVENTURES)
                    verify(customLocationProvider, atLeastOnce()).enable()
                }

                context("when location provider emits new location") {

                    it("notifies view about new location") {
                        presenter.subscribe(view, MapMode.ADVENTURES)
                        testScheduler.triggerActions()
                        customLocationProvider.onLocationChanged(location)
                        testScheduler.triggerActions()
                        verify(view, atLeastOnce()).showCurrentLocation(position)
                    }

                }

                context("when in ${MapMode.STARTED_ADVENTURE.name} map mode") {
                    val adventurePoints = emptyList<AdventurePoint>()
                    val adventure = mock<Adventure>()
                    val adventureId = "adv-id"

                    beforeEachTest {
                        reset(view)
                        whenever(adventureRepository.getAdventureCompletedPoints(adventureId)).thenReturn(Single.just(adventurePoints))
                        whenever(adventure.adventureId).thenReturn(adventureId)
                        presenter.adventure = adventure
                        presenter.subscribe(view, MapMode.STARTED_ADVENTURE)
                        testScheduler.triggerActions()
                    }

                    it("fetches adventure points") {
                        verify(adventureRepository, atLeastOnce()).getAdventureCompletedPoints(
                                adventureId
                        )
                    }

                    it("passes adventure points to the view") {
                        verify(view, atLeastOnce()).showAdventurePoints(adventurePoints)
                    }

                }

                context("when the map camera is moving") {
                    val adventures = emptyList<Adventure>()

                    context("when it is the first change emit") {
                        val cameraDetails = CameraDetails(LatLng(1.0, 1.0), 15f)

                        context("when in ${MapMode.ADVENTURES.name} map mode") {

                            beforeEachTest {
                                reset(view)
                                whenever(adventureRepository.getAdventures(cameraDetails.position.latitude, cameraDetails.position.longitude)).thenReturn(Single.just(adventures))
                                presenter.subscribe(view, MapMode.ADVENTURES)
                                testScheduler.triggerActions()
                                presenter.cameraPositionObserver.onNext(cameraDetails)
                                testScheduler.triggerActions()
                            }

                            it("fetches adventures") {
                                verify(adventureRepository, atLeastOnce()).getAdventures(
                                        lat = position.lat,
                                        lng = position.lng
                                )
                            }

                            it("passes adventures to the view") {
                                verify(view, atLeastOnce()).showAdventures(adventures)
                            }

                        }

                        context("when in ${MapMode.STARTED_ADVENTURE.name} map mode") {

                            beforeEachTest {
                                reset(view)
                                presenter.subscribe(view, MapMode.STARTED_ADVENTURE)
                                testScheduler.triggerActions()
                                presenter.cameraPositionObserver.onNext(cameraDetails)
                                testScheduler.triggerActions()
                            }

                            it("doesnt fetches adventures") {
                                verify(adventureRepository, never()).getAdventures(
                                        anyDouble(),
                                        anyDouble()
                                )
                            }

                        }

                    }

                    context("when it is another change emit") {
                        val initialCameraDetails = CameraDetails(LatLng(0.0, 0.0), 15f)

                        context("when the map camera is moved by at least ${GameMapPresenter.DISTANCE_CHANGE} meters") {
                            val cameraDetails = CameraDetails(
                                    SphericalUtil.computeOffset(initialCameraDetails.position, 1 + GameMapPresenter.DISTANCE_CHANGE.toDouble(), 0.0),
                                    initialCameraDetails.zoom
                            )

                            context("when in ${MapMode.ADVENTURES.name} map mode") {

                                beforeEachTest {
                                    presenter.subscribe(view, MapMode.ADVENTURES)
                                    whenever(adventureRepository.getAdventures(initialCameraDetails.position.latitude, initialCameraDetails.position.longitude)).thenReturn(Single.just(adventures))
                                    whenever(adventureRepository.getAdventures(cameraDetails.position.latitude, cameraDetails.position.longitude)).thenReturn(Single.just(adventures))
                                    testScheduler.triggerActions()
                                    presenter.cameraPositionObserver.onNext(initialCameraDetails)
                                    testScheduler.triggerActions()
                                    presenter.cameraPositionObserver.onNext(cameraDetails)
                                    testScheduler.triggerActions()
                                }

                                it("fetches adventures") {
                                    verify(adventureRepository, atLeast(2)).getAdventures(
                                            anyDouble(),
                                            anyDouble()
                                    )
                                    verify(adventureRepository, atLeastOnce()).getAdventures(
                                            lat = cameraDetails.position.latitude,
                                            lng = cameraDetails.position.longitude
                                    )
                                }

                                it("passes adventures to the view") {
                                    verify(view, atLeast(2)).showAdventures(adventures)
                                }
                            }

                            context("when in ${MapMode.STARTED_ADVENTURE.name} map mode") {

                                beforeEachTest {
                                    presenter.subscribe(view, MapMode.STARTED_ADVENTURE)
                                    testScheduler.triggerActions()
                                    presenter.cameraPositionObserver.onNext(initialCameraDetails)
                                    testScheduler.triggerActions()
                                    presenter.cameraPositionObserver.onNext(cameraDetails)
                                    testScheduler.triggerActions()
                                }

                                it("doesnt fetches adventures") {
                                    verify(adventureRepository, never()).getAdventures(
                                            anyDouble(),
                                            anyDouble()
                                    )
                                }

                            }

                        }

                        context("when the map's camera has changed zoom") {
                            val cameraDetails = CameraDetails(
                                    initialCameraDetails.position,
                                    initialCameraDetails.zoom + 1
                            )

                            context("when in ${MapMode.ADVENTURES.name} map mode") {

                                beforeEachTest {
                                    presenter.subscribe(view, MapMode.ADVENTURES)
                                    whenever(adventureRepository.getAdventures(initialCameraDetails.position.latitude, initialCameraDetails.position.longitude)).thenReturn(Single.just(adventures))
                                    whenever(adventureRepository.getAdventures(cameraDetails.position.latitude, cameraDetails.position.longitude)).thenReturn(Single.just(adventures))
                                    testScheduler.triggerActions()
                                    presenter.cameraPositionObserver.onNext(initialCameraDetails)
                                    testScheduler.triggerActions()
                                    presenter.cameraPositionObserver.onNext(cameraDetails)
                                    testScheduler.triggerActions()
                                }

                                it("fetches adventures") {
                                    verify(adventureRepository, atLeast(2)).getAdventures(
                                            anyDouble(),
                                            anyDouble()
                                    )
                                    verify(adventureRepository, atLeastOnce()).getAdventures(
                                            lat = cameraDetails.position.latitude,
                                            lng = cameraDetails.position.longitude
                                    )
                                }

                                it("passes adventures to the view") {
                                    verify(view, atLeast(2)).showAdventures(adventures)
                                }

                            }

                            context("when in ${MapMode.STARTED_ADVENTURE.name} map mode") {

                                beforeEachTest {
                                    presenter.subscribe(view, MapMode.STARTED_ADVENTURE)
                                    testScheduler.triggerActions()
                                    presenter.cameraPositionObserver.onNext(initialCameraDetails)
                                    testScheduler.triggerActions()
                                    presenter.cameraPositionObserver.onNext(cameraDetails)
                                    testScheduler.triggerActions()
                                }

                                it("doesnt fetches adventures") {
                                    verify(adventureRepository, never()).getAdventures(
                                            anyDouble(),
                                            anyDouble()
                                    )
                                }

                            }

                        }

                    }
                }

                context("when the map camera is not moving") {
                    val adventures = emptyList<Adventure>()
                    val cameraDetails = CameraDetails(LatLng(1.0, 1.0), 15f)

                    context("when ${GameMapPresenter.LOAD_ADVENTURES_TIMEOUT} seconds passed") {

                        context("when in ${MapMode.ADVENTURES.name} map mode") {

                            beforeEachTest {
                                reset(view)
                                whenever(adventureRepository.getAdventures(any(), any())).thenReturn(Single.just(adventures))
                                presenter.subscribe(view, MapMode.ADVENTURES)
                                testScheduler.triggerActions()
                                presenter.cameraPositionObserver.onNext(cameraDetails) // first emit
                                testScheduler.advanceTimeBy(GameMapPresenter.LOAD_ADVENTURES_TIMEOUT, TimeUnit.SECONDS)

                            }

                            it("fetches adventures") {
                                verify(adventureRepository, atLeast(2)).getAdventures(
                                        anyDouble(),
                                        anyDouble()
                                )
                                verify(adventureRepository, atLeastOnce()).getAdventures(
                                        lat = cameraDetails.position.latitude,
                                        lng = cameraDetails.position.longitude
                                )
                            }

                            it("passes adventures to the view") {
                                verify(view, atLeast(2)).showAdventures(adventures)
                            }

                        }

                        context("when in ${MapMode.STARTED_ADVENTURE.name} map mode") {

                            beforeEachTest {
                                presenter.subscribe(view, MapMode.STARTED_ADVENTURE)
                                testScheduler.triggerActions()
                                presenter.cameraPositionObserver.onNext(cameraDetails) // first emit
                                testScheduler.advanceTimeBy(GameMapPresenter.LOAD_ADVENTURES_TIMEOUT, TimeUnit.SECONDS)
                            }

                            it("doesnt fetches adventures") {
                                verify(adventureRepository, never()).getAdventures(
                                        anyDouble(),
                                        anyDouble()
                                )
                            }

                        }

                    }

                }

            }

        }

        describe("unsubscribe") {

            it("disables location provider") {
                simplePresenter.unsubscribe()
                verify(locationProvider, atLeastOnce()).disable()
            }

        }

    }

})