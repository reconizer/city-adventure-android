package pl.reconizer.cityadventure.presentation.map.game

import android.location.Location
import android.util.Log
import io.reactivex.Scheduler
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.presentation.common.rx.CallbackWrapper
import pl.reconizer.cityadventure.presentation.errorhandlers.ErrorHandler
import pl.reconizer.cityadventure.presentation.location.ILocationProvider
import pl.reconizer.cityadventure.presentation.mvp.BasePresenter
import java.lang.ref.WeakReference

class GameMapPresenter(
        private val backgroundScheduler: Scheduler,
        private val mainScheduler: Scheduler,
        private val locationProvider: ILocationProvider,
        private val errorHandler: ErrorHandler<Error>
) : BasePresenter<IGameMapView>() {

    override fun subscribe(view: IGameMapView) {
        super.subscribe(view)
        errorHandler.view = WeakReference(view)
        if (locationProvider.hasPermission) {
            locationProvider.locationChange
                    .subscribeOn(backgroundScheduler)
                    .observeOn(mainScheduler)
                    .subscribeWith(object : CallbackWrapper<Location, Error>(errorHandler) {
                        override fun onComplete() {}

                        override fun onNext(t: Location) {
                            Log.d("GameMap", "Location: (${t.latitude}, ${t.longitude}")
                            this@GameMapPresenter.view?.showCurrentLocation(t)
                        }


                    })
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