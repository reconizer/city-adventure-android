package pl.reconizer.cityadventure.presentation.map.game

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import pl.reconizer.cityadventure.common.extensions.toLatLng
import pl.reconizer.cityadventure.common.extensions.toPosition
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.entities.Position
import pl.reconizer.cityadventure.domain.usecases.adventure.GetAdventures
import pl.reconizer.cityadventure.presentation.common.rx.CallbackWrapper
import pl.reconizer.cityadventure.presentation.errorhandlers.ErrorHandler
import pl.reconizer.cityadventure.presentation.location.ILocationProvider
import pl.reconizer.cityadventure.presentation.map.CameraDetails
import pl.reconizer.cityadventure.presentation.mvp.BasePresenter
import java.lang.IllegalArgumentException
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

class GameMapPresenter(
        private val backgroundScheduler: Scheduler,
        private val mainScheduler: Scheduler,
        private val locationProvider: ILocationProvider,
        private val getAdventures: GetAdventures,
        private val errorHandler: ErrorHandler<Error>
) : BasePresenter<IGameMapView>() {

    private var previousCameraDetails: CameraDetails? = null

    val cameraPositionObserver: PublishSubject<CameraDetails> = PublishSubject.create()

    val lastLocation: LatLng?
        get() {
            return if (locationProvider.lastLocation == null) {
                null
            } else {
                locationProvider.lastLocation!!.toLatLng()
            }
        }

    private val cameraMovedByDistanceObservable = cameraPositionObserver
            .filter {
                previousCameraDetails == null ||
                        SphericalUtil.computeDistanceBetween(it.position, previousCameraDetails!!.position) > DISTANCE_CHANGE ||
                        it.zoom != previousCameraDetails!!.zoom
            }
            .doAfterNext { previousCameraDetails = it }
            .map { Position(it.position.latitude, it.position.longitude) }
            .share()

    private val loadingIntervalsObservable = Observable.interval(LOAD_ADVENTURES_TIMEOUT, LOAD_ADVENTURES_TIMEOUT, TimeUnit.SECONDS)
            .filter { previousCameraDetails != null }
            .share()

    override fun subscribe(view: IGameMapView) {
        super.subscribe(view)
        errorHandler.view = WeakReference(view)
        if (locationProvider.hasPermission) {
            val locationChangeObservable = locationProvider.locationChange.share()
            disposables.add(
                    locationChangeObservable
                        .subscribeOn(backgroundScheduler)
                        .observeOn(mainScheduler)
                        .map { it.toPosition() }
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
                            .flatMapSingle {
                                Log.d("GameMapPresenter", "Checking pins")
                                when(it) {
                                    is Long -> {
                                        Log.d("GameMapPresenter", "Checking pins - because of time")
                                        getAdventures(Position(previousCameraDetails!!.position.latitude, previousCameraDetails!!.position.longitude))
                                    }
                                    is Position -> {
                                        Log.d("GameMapPresenter", "Checking pins - because of move")
                                        getAdventures(it)
                                    }
                                    else -> Single.error(IllegalArgumentException())
                                }
                            }
                            .subscribeOn(backgroundScheduler)
                            .observeOn(mainScheduler)
                            .subscribeWith(object : CallbackWrapper<List<Adventure>, Error>(errorHandler) {
                                override fun onNext(t: List<Adventure>) {
                                    this@GameMapPresenter.view?.showAdventures(t)
                                }

                                override fun onComplete() {}

                            })
            )
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
        const val LOAD_ADVENTURES_TIMEOUT = 5L // in seconds
    }
}