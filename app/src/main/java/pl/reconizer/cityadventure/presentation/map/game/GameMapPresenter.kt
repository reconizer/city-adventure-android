package pl.reconizer.cityadventure.presentation.map.game

import android.util.Log
import com.google.maps.android.SphericalUtil
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.subjects.PublishSubject
import pl.reconizer.cityadventure.common.extensions.toPosition
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.entities.AdventurePoint
import pl.reconizer.cityadventure.domain.entities.Position
import pl.reconizer.cityadventure.domain.repositories.IAdventureRepository
import pl.reconizer.cityadventure.presentation.common.rx.CallbackWrapper
import pl.reconizer.cityadventure.presentation.common.rx.SingleCallbackWrapper
import pl.reconizer.cityadventure.presentation.errorhandlers.ErrorHandler
import pl.reconizer.cityadventure.presentation.location.ILocationProvider
import pl.reconizer.cityadventure.presentation.map.CameraDetails
import pl.reconizer.cityadventure.presentation.map.MapMode
import pl.reconizer.cityadventure.presentation.mvp.BasePresenter
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
            return if (locationProvider.lastLocation == null) {
                null
            } else {
                locationProvider.lastLocation!!.toPosition()
            }
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
            val locationChangeObservable = locationProvider.locationChange.share()
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

    companion object {
        const val DISTANCE_CHANGE = 500 // in meters
        const val LOAD_ADVENTURES_TIMEOUT = 30L // in seconds
    }
}