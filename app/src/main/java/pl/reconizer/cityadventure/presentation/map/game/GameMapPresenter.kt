package pl.reconizer.cityadventure.presentation.map.game

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Observable
import io.reactivex.Scheduler
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.domain.entities.Position
import pl.reconizer.cityadventure.presentation.common.rx.CallbackWrapper
import pl.reconizer.cityadventure.presentation.errorhandlers.ErrorHandler
import pl.reconizer.cityadventure.presentation.location.ILocationProvider
import pl.reconizer.cityadventure.presentation.mvp.BasePresenter
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

class GameMapPresenter(
        private val backgroundScheduler: Scheduler,
        private val mainScheduler: Scheduler,
        private val locationProvider: ILocationProvider,
        private val errorHandler: ErrorHandler<Error>
) : BasePresenter<IGameMapView>() {

    val lastLocation: LatLng?
        get() {
            return if (locationProvider.lastLocation == null) {
                null
            } else {
                LatLng(locationProvider.lastLocation!!.latitude, locationProvider.lastLocation!!.longitude)
            }
        }

    override fun subscribe(view: IGameMapView) {
        super.subscribe(view)
        errorHandler.view = WeakReference(view)
        if (locationProvider.hasPermission) {
            val locationChangeObservable = locationProvider.locationChange.share()
            disposables.add(
                    locationChangeObservable
                        .subscribeOn(backgroundScheduler)
                        .observeOn(mainScheduler)
                        .map { LatLng(it.latitude, it.longitude) }
                        .subscribeWith(object : CallbackWrapper<LatLng, Error>(errorHandler) {
                            override fun onComplete() {}

                            override fun onNext(t: LatLng) {
                                Log.d("GameMap", "Location: (${t.latitude}, ${t.longitude}")
                                this@GameMapPresenter.view?.showCurrentLocation(t)
                            }


                        })
            )
            disposables.add(
                    Observable.concat(
                            locationChangeObservable,
                            Observable.interval(30,30, TimeUnit.SECONDS)
                    )
                            .doAfterNext {
                                when(it) {
                                    is Long -> {

                                    }
                                    is Position -> {}
                                }
                            }
                            .subscribeOn(backgroundScheduler)
                            .observeOn(mainScheduler)
                            .subscribe()
            )
            locationProvider.enable()
        } else {
            this@GameMapPresenter.view?.requestLocationPermission()
        }
    }

    override fun unsubscribe() {
        super.unsubscribe()
        locationProvider.disable()
    }
}