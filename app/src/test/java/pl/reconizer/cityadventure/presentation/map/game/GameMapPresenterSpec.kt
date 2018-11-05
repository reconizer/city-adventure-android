package pl.reconizer.cityadventure.presentation.map.game

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import com.nhaarman.mockitokotlin2.*
import com.winterbe.expekt.expect
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.spekframework.spek2.Spek
import org.spekframework.spek2.dsl.Skip
import org.spekframework.spek2.style.specification.describe
import pl.reconizer.cityadventure.common.extensions.toPosition
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.entities.Position
import pl.reconizer.cityadventure.domain.usecases.adventure.GetAdventures
import pl.reconizer.cityadventure.presentation.errorhandlers.ErrorHandler
import pl.reconizer.cityadventure.presentation.location.ILocationProvider
import pl.reconizer.cityadventure.presentation.map.CameraDetails
import java.util.concurrent.TimeUnit

class GameMapPresenterSpec : Spek({

    describe("GameMapPresenter") {
        lateinit var testScheduler: TestScheduler
        lateinit var simplePresenter: GameMapPresenter

        val locationProvider = mock<ILocationProvider>()
        val getAdventures =  mock<GetAdventures>()
        val errorHandler = mock<ErrorHandler<Error>>()

        val view = mock<IGameMapView>()

        val location = mock<Location>()
        val position = Position(1.0, 1.0)

        before {
            whenever(location.latitude).thenReturn(position.lat)
            whenever(location.longitude).thenReturn(position.lng)
        }

        beforeEachTest {
            reset(getAdventures, errorHandler)
            reset(errorHandler)
            testScheduler = TestScheduler()
            simplePresenter = GameMapPresenter(
                    Schedulers.trampoline(),
                    Schedulers.trampoline(),
                    locationProvider,
                    getAdventures,
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
                            getAdventures,
                            errorHandler
                    )
                }

                afterEachTest { presenter.unsubscribe() }

                it("enables location provider") {
                    presenter.subscribe(view)
                    verify(customLocationProvider, atLeastOnce()).enable()
                }

                context("when location provider emits new location") {

                    it("notifies view about new location") {
                        presenter.subscribe(view)
                        testScheduler.triggerActions()
                        customLocationProvider.onLocationChanged(location)
                        testScheduler.triggerActions()
                        verify(view, atLeastOnce()).showCurrentLocation(position)
                    }

                }

                context("when the map camera is moving") {
                    val adventures = emptyList<Adventure>()

                    context("when it is the first change emit") {
                        val cameraDetails = CameraDetails(LatLng(1.0, 1.0), 15f)

                        beforeEachTest {
                            reset(view)
                            whenever(getAdventures.invoke(any())).thenReturn(Single.just(adventures))
                            presenter.subscribe(view)
                            testScheduler.triggerActions()
                            presenter.cameraPositionObserver.onNext(cameraDetails)
                            testScheduler.triggerActions()
                        }

                        it("fetches adventures") {
                            verify(getAdventures, atLeastOnce()).invoke(position)
                        }

                        it("passes adventures to the view") {
                            verify(view, atLeastOnce()).showAdventures(adventures)
                        }

                    }

                    context("when it is another change emit") {
                        val initialCameraDetails = CameraDetails(LatLng(0.0, 0.0), 15f)

                        beforeEachTest {
                            reset(view)
                            whenever(getAdventures.invoke(any())).thenReturn(Single.just(adventures))
                            presenter.subscribe(view)
                            testScheduler.triggerActions()
                            presenter.cameraPositionObserver.onNext(initialCameraDetails)
                            testScheduler.triggerActions()
                        }

                        context("when the map camera is moved by at least ${GameMapPresenter.DISTANCE_CHANGE} meters") {
                            val cameraDetails = CameraDetails(
                                    SphericalUtil.computeOffset(initialCameraDetails.position, 1 + GameMapPresenter.DISTANCE_CHANGE.toDouble(), 0.0),
                                    initialCameraDetails.zoom
                            )

                            beforeEachTest {
                                presenter.cameraPositionObserver.onNext(cameraDetails)
                                testScheduler.triggerActions()
                            }

                            it("fetches adventures") {
                                verify(getAdventures, atLeast(2)).invoke(any())
                                verify(getAdventures, atLeastOnce()).invoke(cameraDetails.position.toPosition())
                            }

                            it("passes adventures to the view") {
                                verify(view, atLeast(2)).showAdventures(adventures)
                            }

                        }

                        context("when the map's camera has changed zoom") {
                            val cameraDetails = CameraDetails(
                                    initialCameraDetails.position,
                                    initialCameraDetails.zoom + 1
                            )

                            beforeEachTest {
                                presenter.cameraPositionObserver.onNext(cameraDetails)
                                testScheduler.triggerActions()
                            }

                            it("fetches adventures") {
                                verify(getAdventures, atLeast(2)).invoke(any())
                                verify(getAdventures, atLeastOnce()).invoke(cameraDetails.position.toPosition())
                            }

                            it("passes adventures to the view") {
                                verify(view, atLeast(2)).showAdventures(adventures)
                            }

                        }
                    }
                }

                context("when the map camera is not moving") {
                    val adventures = emptyList<Adventure>()
                    val cameraDetails = CameraDetails(LatLng(1.0, 1.0), 15f)

                    beforeEachTest {
                        reset(view)
                        whenever(getAdventures.invoke(any())).thenReturn(Single.just(adventures))
                        presenter.subscribe(view)
                        testScheduler.triggerActions()
                        presenter.cameraPositionObserver.onNext(cameraDetails) // first emit
                    }

                    afterEachTest { presenter.unsubscribe() }

                    context("when ${GameMapPresenter.LOAD_ADVENTURES_TIMEOUT} seconds passed") {

                        beforeEachTest {
                            testScheduler.advanceTimeBy(GameMapPresenter.LOAD_ADVENTURES_TIMEOUT, TimeUnit.SECONDS)
                        }

                        it("fetches adventures") {
                            verify(getAdventures, atLeast(2)).invoke(any())
                            verify(getAdventures, atLeastOnce()).invoke(cameraDetails.position.toPosition())
                        }

                        it("passes adventures to the view") {
                            verify(view, atLeast(2)).showAdventures(adventures)
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