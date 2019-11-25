package pl.reconizer.unfold.presentation.map.game

import android.location.Location
import android.location.LocationListener
import com.gojuno.koptional.None
import com.gojuno.koptional.Some
import com.gojuno.koptional.toOptional
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import pl.reconizer.unfold.BuildConfig
import pl.reconizer.unfold.common.extensions.toPosition
import pl.reconizer.unfold.domain.entities.*
import pl.reconizer.unfold.domain.entities.puzzles.PuzzleAnswerForm
import pl.reconizer.unfold.domain.entities.puzzles.PuzzleResponse
import pl.reconizer.unfold.domain.repositories.IAdventureRepository
import pl.reconizer.unfold.domain.repositories.IUserRepository
import pl.reconizer.unfold.presentation.common.errorshandlers.ErrorsHandler
import pl.reconizer.unfold.presentation.common.rx.CallbackWrapper
import pl.reconizer.unfold.presentation.common.rx.MaybeCallbackWrapper
import pl.reconizer.unfold.presentation.common.rx.SingleCallbackWrapper
import pl.reconizer.unfold.presentation.location.DebugLocationProvider
import pl.reconizer.unfold.presentation.location.GpsInterfaceStatus
import pl.reconizer.unfold.presentation.location.ILocationProvider
import pl.reconizer.unfold.presentation.map.CameraDetails
import pl.reconizer.unfold.presentation.map.MapMode
import pl.reconizer.unfold.presentation.mvp.BasePresenter
import retrofit2.HttpException
import timber.log.Timber
import java.lang.ref.WeakReference
import java.util.*
import java.util.concurrent.TimeUnit

class GameMapPresenter(
        private val backgroundScheduler: Scheduler,
        private val mainScheduler: Scheduler,
        private val locationProvider: ILocationProvider,
        private val adventureRepository: IAdventureRepository,
        private val userRepository: IUserRepository,
        private val errorsHandler: ErrorsHandler
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

    /**
     * Triggers when user 'pushes' new camera position and it is moved by DISTANCE_CHANGE
     * or camera's zoom is changed
     */
    private val cameraMovedByDistanceObservable: Observable<Position> = cameraPositionObserver
            .observeOn(backgroundScheduler)
            .filter {
                previousCameraDetails == null ||
                        SphericalUtil.computeDistanceBetween(it.position, previousCameraDetails!!.position) > DISTANCE_CHANGE ||
                        it.zoom != previousCameraDetails!!.zoom
            }
            .doOnNext { previousCameraDetails = it }
            .map { it.position.toPosition() }
            .share()

    private val loadingIntervalsObservable: Observable<Position> = Observable.interval(0, LOAD_ADVENTURES_TIMEOUT, TimeUnit.SECONDS, backgroundScheduler)
            .observeOn(backgroundScheduler)
            .flatMap {
                if (previousCameraDetails != null) {
                    Observable.just(Some(previousCameraDetails!!.position.toPosition()))
                } else if (lastLocation != null) {
                    Observable.just(Some(lastLocation!!))
                } else {
                    Observable.just(None)
                }
            }
            .filter { it is Some }
            .map { it.toNullable()!! }
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
                        .subscribeWith(object : CallbackWrapper<Position>(errorsHandler) {
                            override fun onComplete() {}

                            override fun onNext(t: Position) {
                                Timber.d("Location: (${t.lat}, ${t.lng})")
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
                                adventureRepository.getAdventures(
                                        lat = it.lat,
                                        lng = it.lng
                                )
                            }
                            .observeOn(mainScheduler)
                            .subscribeWith(object : CallbackWrapper<List<MapAdventure>>(errorsHandler) {
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
                                adventureRepository.getAdventures(
                                        lat = it.latitude,
                                        lng = it.longitude
                                ).toMaybe()
                            }
                            .observeOn(mainScheduler)
                            .subscribeWith(object : MaybeCallbackWrapper<List<MapAdventure>>(errorsHandler) {
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
                                .subscribeWith(object : SingleCallbackWrapper<List<AdventurePoint>>(errorsHandler) {
                                    override fun onSuccess(t: List<AdventurePoint>) {
                                        this@GameMapPresenter.view?.showAdventurePoints(t)
                                    }
                                })
                )
                disposables.add(
                        Observable.interval(0L, 1000L, TimeUnit.MILLISECONDS)
                                .subscribeOn(backgroundScheduler)
                                .observeOn(mainScheduler)
                                .subscribeWith(object : CallbackWrapper<Long>(errorsHandler) {
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
        lastLocation?.let { certainLastLocation ->
            disposables.add(
                    adventureRepository.resolvePoint(PuzzleAnswerForm(
                            certainLastLocation,
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
                            .subscribeWith(object : SingleCallbackWrapper<Any>(errorsHandler) {
                                override fun onSuccess(t: Any) {
                                    when(t) {
                                        is List<*> -> this@GameMapPresenter.view?.showAdventurePoints(t as List<AdventurePoint>)
                                        is PuzzleResponse -> view?.finishAdventure()
                                    }

                                }
                            })
            )
        }
    }

    fun resolvePoint(point: AdventurePoint) {
        lastLocation?.let { certainLastLocation ->
            disposables.add(
                    adventureRepository.resolvePoint(PuzzleAnswerForm(
                            certainLastLocation,
                            adventure!!.adventureId
                    ))
                            .subscribeOn(backgroundScheduler)
                            .observeOn(mainScheduler)
                            .subscribeWith(object : SingleCallbackWrapper<PuzzleResponse>(errorsHandler) {

                                override fun onError(e: Throwable) {
                                    if (e is HttpException && e.code() == 422) {
                                        view?.resolveingPointFailed(point)
                                    }
                                    super.onError(e)
                                }

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
    }

    fun fetchStartPoint() {
        adventure?.adventureId?.let {
            disposables.add(
                    adventureRepository.getAdventure(it)
                            .subscribeOn(backgroundScheduler)
                            .observeOn(mainScheduler)
                            .subscribeWith(object : SingleCallbackWrapper<AdventureStartPoint>(errorsHandler) {
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
                        .subscribeWith(object : SingleCallbackWrapper<Int>(errorsHandler) {
                            override fun onSuccess(t: Int) {
                                view?.showNumberOfActiveAdventures(t)
                            }
                        })
        )
    }

    fun handleClickedMapPosition(position: LatLng) {
        if (BuildConfig.BUILD_TYPE.contentEquals("debug") || BuildConfig.BUILD_TYPE.contentEquals("internalTests")) {
            if (locationProvider is LocationListener) {
                locationProvider.onLocationChanged(Location("").apply {
                    latitude = position.latitude
                    longitude = position.longitude
                })
            }
        }
    }

    companion object {
        const val DISTANCE_CHANGE = 500 // in meters
        const val LOAD_ADVENTURES_TIMEOUT = 30L // in seconds
    }
}