package pl.reconizer.unfold.presentation.location

import android.location.Location
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

interface ILocationProvider {

    val lastLocation: Location?

    val statusChange: PublishSubject<GpsInterfaceStatus>
    val locationChange: PublishSubject<Location>
    val lastLocationChange: BehaviorSubject<Location>
    val isEnabled: Boolean
    val interfaceStatus: GpsInterfaceStatus
    val canCollectLocation: Boolean
    val hasPermission: Boolean

    fun enable()
    fun disable()
}