package pl.reconizer.cityadventure.presentation.location

import android.location.Location
import io.reactivex.subjects.PublishSubject

interface ILocationProvider {

    val lastLocation: Location?
    val changeDistance: Double

    val statusChange: PublishSubject<GpsInterfaceStatus>
    val locationChange: PublishSubject<Location>
    val isEnabled: Boolean
    val interfaceStatus: GpsInterfaceStatus
    val canCollectLocation: Boolean
    val hasPermission: Boolean

    fun enable()
    fun disable()
}