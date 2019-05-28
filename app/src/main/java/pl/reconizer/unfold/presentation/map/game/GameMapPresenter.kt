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
import pl.reconizer.unfold.domain.repositories.IAdventureRepository
import pl.reconizer.unfold.presentation.common.rx.CallbackWrapper
import pl.reconizer.unfold.presentation.common.rx.MaybeCallbackWrapper
import pl.reconizer.unfold.presentation.common.rx.SingleCallbackWrapper
import pl.reconizer.unfold.presentation.errorhandlers.ErrorHandler
import pl.reconizer.unfold.presentation.location.GpsInterfaceStatus
import pl.reconizer.unfold.presentation.location.ILocationProvider
import pl.reconizer.unfold.presentation.map.CameraDetails
import pl.reconizer.unfold.presentation.map.MapMode
import pl.reconizer.unfold.presentation.mvp.BasePresenter
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

class GameMapPresenter(
        private val backgroundScheduler: Scheduler,
        private val mainScheduler: Scheduler,
        private val locationProvider: ILocationProvider,
        private val adventureRepository: IAdventureRepository,
        private val errorHandler: ErrorHandler<Error>
) : BasePresenter<IGameMapView>() {

    private var previousCameraDetails: CameraDetails? = null

    private lateinit var mapMode: MapMode

    var adventure: Adventure? = null

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
        errorHandler.view = WeakReference(view)
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
                        .subscribeWith(object : CallbackWrapper<Position, Error>(errorHandler) {
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
                            .subscribeWith(object : CallbackWrapper<List<Adventure>, Error>(errorHandler) {
                                override fun onNext(t: List<Adventure>) {
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
                            .subscribeWith(object : MaybeCallbackWrapper<List<Adventure>, Error>(errorHandler) {
                                override fun onComplete() {}

                                override fun onSuccess(t: List<Adventure>) {
                                    this@GameMapPresenter.view?.showAdventures(t)
                                }
                            })
            )
            if (mapMode == MapMode.STARTED_ADVENTURE && adventure != null) {
                disposables.add(
                        adventureRepository.getAdventureCompletedPoints(adventure!!.adventureId)
                                .subscribeOn(backgroundScheduler)
                                .observeOn(mainScheduler)
                                .subscribeWith(object : SingleCallbackWrapper<List<AdventurePoint>, Error>(errorHandler) {
                                    override fun onSuccess(t: List<AdventurePoint>) {
                                        this@GameMapPresenter.view?.showAdventurePoints(t)
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
                        .subscribeWith(object : SingleCallbackWrapper<Any, Error>(errorHandler) {
                            override fun onSuccess(t: Any) {
                                when(t) {
                                    is List<*> -> this@GameMapPresenter.view?.showAdventurePoints(t as List<AdventurePoint>)
                                    is PuzzleResponse -> view?.showSummary()
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
                        .subscribeWith(object : SingleCallbackWrapper<PuzzleResponse, Error>(errorHandler) {
                            override fun onSuccess(t: PuzzleResponse) {
                                if (t.isCompleted) {
                                    if (t.isLastPoint) {
                                        view?.showSummary()
                                    }
                                } else {
                                    view?.showPuzzle(point, t)
                                }
                            }
                        })
        )
    }

    companion object {
        const val DISTANCE_CHANGE = 500 // in meters
        const val LOAD_ADVENTURES_TIMEOUT = 30L // in seconds
    }
}