package pl.reconizer.cityadventure.presentation.map.game

import android.location.Location
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import pl.reconizer.cityadventure.presentation.location.GpsInterfaceStatus
import pl.reconizer.cityadventure.presentation.location.ILocationProvider

class MockLocationProvider : ILocationProvider {

    override var lastLocation: Location? = null

    override val statusChange = PublishSubject.create<GpsInterfaceStatus>()

    override val locationChange = PublishSubject.create<Location>()

    override val lastLocationChange = BehaviorSubject.create<Location>()

    override val isEnabled = true

    override val interfaceStatus = GpsInterfaceStatus.UP

    override val canCollectLocation = true

    override val hasPermission = true

    override fun enable() {
    }

    override fun disable() {
    }

    fun onLocationChanged(location: Location) {
        lastLocation = location
        locationChange.onNext(location)
        lastLocationChange.onNext(location)
    }
}