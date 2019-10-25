package pl.reconizer.unfold.presentation.map.game

import android.util.Log
import com.google.maps.android.SphericalUtil
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import pl.reconizer.unfold.common.extensions.toPosition
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.domain.entities.*
import pl.reconizer.unfold.domain.entities.puzzles.PuzzleAnswerForm
import pl.reconizer.unfold.domain.entities.puzzles.PuzzleResponse
import pl.reconizer.unfold.domain.repositories.IAdventureRepository
import pl.reconizer.unfold.domain.repositories.IUserRepository
import pl.reconizer.unfold.presentation.common.rx.CallbackWrapper
import pl.reconizer.unfold.presentation.common.rx.MaybeCallbackWrapper
import pl.reconizer.unfold.presentation.common.rx.SingleCallbackWrapper
import pl.reconizer.unfold.presentation.errorhandlers.ErrorsHandler
import pl.reconizer.unfold.presentation.location.GpsInterfaceStatus
import pl.reconizer.unfold.presentation.location.ILocationProvider
import pl.reconizer.unfold.presentation.map.CameraDetails
import pl.reconizer.unfold.presentation.map.MapMode
import pl.reconizer.unfold.presentation.mvp.BasePresenter
import java.lang.ref.WeakReference
import java.util.*
import java.util.concurrent.TimeUnit

class GameMapPresenter(
        private val backgroundScheduler: Scheduler,
        private val mainScheduler: Scheduler,
        private val locationProvider: ILocationProvider,
        private val adventureRepository: IAdventureRepository,
        private val userRepository: IUserRepository,
        private val errorsHandler: ErrorsHandler<Error>
) : BasePresenter<IGameMapView>() {

    private var previousCameraDetails: CameraDetails? = null

    private lateinit var mapMode: MapMode

    var adventure: MapAdventure? = null

    var adventureStartPoint: AdventureStartPoint? = null
        private set

    val cameraPositionObserver: PublishSubject<CameraDetails> = PublishSubject.create()

    val lastLocation: Position?
        get() {
            return locationProvider.lastLocation?.toPosition()
        }

    private val cameraMovedByDistanceObservable = cameraPositionObserver
            .observeOn(backgroundScheduler)
            .filter {
                previousCameraDetails == null ||
                        SphericalUtil.computeDistanceBetween(it.position, previousCameraDetails!!.position) > DISTANCE_CHANGE ||
                        it.zoom != previousCameraDetails!!.zoom
            }
            .doOnNext { previousCameraDetails = it }
            .map { it.position.toPosition() }
            .share()

    private val loadingIntervalsObservable = Observable.interval(LOAD_ADVENTURES_TIMEOUT, LOAD_ADVENTURES_TIMEOUT, TimeUnit.SECONDS, backgroundScheduler)
            .observeOn(backgroundScheduler)
            .filter { previousCameraDetails != null }
            .map { previousCameraDetails!!.position.toPosition() }
            .share()

    fun subscribe(view: IGameMapView, mode: MapMode) {
        mapMode = mode
        subscribe(view)
    }

    override fun subscribe(view: IGameMapView) {
        super.subscribe(view)
        errorsHandler.view = WeakReference(view)
        if (locationProvider.hasPermission) {
            val locationChangeObservable = locationProvider.lastLocationChange.share()
            disposables.add(
                    locationProvider.statusChange
                            .subscribeOn(backgroundScheduler)
                            .observeOn(mainScheduler)
                            .subscribe {
                                when(it) {
                                    GpsInterfaceStatus.DOWN -> this@GameMapPresenter.view?.gpsUnavailable()
                                    GpsInterfaceStatus.UP -> this@GameMapPresenter.view?.gpsAvailableAgain()
                                }
                            }
            )
            disposables.add(
                    locationChangeObservable
                        .subscribeOn(backgroundScheduler)
                        .map { it.toPosition() }
                        .observeOn(mainScheduler)
                        .subscribeWith(object : CallbackWrapper<Position, Error>(errorsHandler) {
                            override fun onComplete() {}

                            override fun onNext(t: Position) {
                                Log.d("GameMap", "Location: (${t.lat}, ${t.lng})")
                                this@GameMapPresenter.view?.showCurrentLocation(t)
                            }
                        })
            )
            disposables.add(
                    Observable.merge(
                            cameraMovedByDistanceObservable,
                            loadingIntervalsObservable
                    )
                            .filter { mapMode == MapMode.ADVENTURES }
                            .subscribeOn(backgroundScheduler)
                            .observeOn(backgroundScheduler)
                            .flatMapSingle {
                                Log.d("GameMapPresenter", "Checking pins")
                                adventureRepository.getAdventures(
                                        lat = it.lat,
                                        lng = it.lng
                                )
                            }
                            .observeOn(mainScheduler)
                            .subscribeWith(object : CallbackWrapper<List<MapAdventure>, Error>(errorsHandler) {
                                override fun onNext(t: List<MapAdventure>) {
                                    this@GameMapPresenter.view?.showAdventures(t)
                                }

                                override fun onComplete() {}
                            })
            )
            disposables.add(
                    locationChangeObservable.firstElement()
                            .filter { mapMode == MapMode.ADVENTURES }
                            .subscribeOn(backgroundScheduler)
                            .observeOn(backgroundScheduler)
                            .flatMap {
                                Log.d("GameMapPresenter", "Checking pins")
                                adventureRepository.getAdventures(
                                        lat = it.latitude,
                                        lng = it.longitude
                                ).toMaybe()
                            }
                            .observeOn(mainScheduler)
                            .subscribeWith(object : MaybeCallbackWrapper<List<MapAdventure>, Error>(errorsHandler) {
                                override fun onComplete() {}

                                override fun onSuccess(t: List<MapAdventure>) {
                                    this@GameMapPresenter.view?.showAdventures(t)
                                }
                            })
            )
            if (mapMode == MapMode.STARTED_ADVENTURE && adventure != null) {
                disposables.add(
                        adventureRepository.getAdventureCompletedPoints(adventure!!.adventureId)
                                .subscribeOn(backgroundScheduler)
                                .observeOn(mainScheduler)
                                .subscribeWith(object : SingleCallbackWrapper<List<AdventurePoint>, Error>(errorsHandler) {
                                    override fun onSuccess(t: List<AdventurePoint>) {
                                        this@GameMapPresenter.view?.showAdventurePoints(t)
                                    }
                                })
                )
                disposables.add(
                        Observable.interval(0L, 1000L, TimeUnit.MILLISECONDS)
                                .subscribeOn(backgroundScheduler)
                                .observeOn(mainScheduler)
                                .subscribeWith(object : CallbackWrapper<Long, Error>(errorsHandler) {
                                    override fun onComplete() {}

                                    override fun onNext(t: Long) {
                                        adventureStartPoint?.let { certainAdventureStartPoint ->
                                            if (certainAdventureStartPoint.startedAt != null) {
                                                val currentTimestamp = Calendar.getInstance().timeInMillis
                                                this@GameMapPresenter.view?.updateAdventureTimer(currentTimestamp - certainAdventureStartPoint.startedAt.time)
                                            }
                                        }
                                    }
                                })
                )
            }
            locationProvider.enable()
        } else {
            this@GameMapPresenter.view?.requestLocationPermission()
        }
    }

    override fun unsubscribe() {
        super.unsubscribe()
        locationProvider.disable()
        previousCameraDetails = null
    }

    fun checkLocation() {
        disposables.add(
                adventureRepository.resolvePoint(PuzzleAnswerForm(
                        locationProvider.lastLocation!!.toPosition(),
                        adventure!!.adventureId
                ))
                        .flatMap {
                            if (it.isCompleted && it.isLastPoint) {
                                Single.just(it)
                            } else {
                                adventureRepository.getAdventureCompletedPoints(adventure!!.adventureId)
                            }
                        }
                        .subscribeOn(backgroundScheduler)
                        .observeOn(mainScheduler)
                        .subscribeWith(object : SingleCallbackWrapper<Any, Error>(errorsHandler) {
                            override fun onSuccess(t: Any) {
                                when(t) {
                                    is List<*> -> this@GameMapPresenter.view?.showAdventurePoints(t as List<AdventurePoint>)
                                    is PuzzleResponse -> view?.finishAdventure()
                                }

                            }
                        })
        )
    }

    fun resolvePoint(point: AdventurePoint) {
        disposables.add(
                adventureRepository.resolvePoint(PuzzleAnswerForm(
                        locationProvider.lastLocation!!.toPosition(),
                        adventure!!.adventureId
                ))
                        .subscribeOn(backgroundScheduler)
                        .observeOn(mainScheduler)
                        .subscribeWith(object : SingleCallbackWrapper<PuzzleResponse, Error>(errorsHandler) {
                            override fun onSuccess(t: PuzzleResponse) {
                                if (t.isCompleted) {
                                    if (t.isLastPoint) {
                                        view?.finishAdventure()
                                    }
                                } else {
                                    view?.showPuzzle(point, t)
                                }
                            }
                        })
        )
    }

    fun fetchStartPoint() {
        adventure?.adventureId?.let {
            disposables.add(
                    adventureRepository.getAdventure(it)
                            .subscribeOn(backgroundScheduler)
                            .observeOn(mainScheduler)
                            .subscribeWith(object : SingleCallbackWrapper<AdventureStartPoint, Error>(errorsHandler) {
                                override fun onSuccess(t: AdventureStartPoint) {
                                    adventureStartPoint = t
                                    view?.showAdventure(t)
                                }
                            })
            )
        }
    }

    fun fetchNumberOfActiveAdventures() {
        disposables.add(
                userRepository.getStartedAdventuresCount()
                        .subscribeOn(backgroundScheduler)
                        .observeOn(mainScheduler)
                        .subscribeWith(object : SingleCallbackWrapper<Int, Error>(errorsHandler) {
                            override fun onSuccess(t: Int) {
                                view?.showNumberOfActiveAdventures(t)
                            }
                        })
        )
    }

    companion object {
        const val DISTANCE_CHANGE = 500 // in meters
        const val LOAD_ADVENTURES_TIMEOUT = 30L // in seconds
    }
}