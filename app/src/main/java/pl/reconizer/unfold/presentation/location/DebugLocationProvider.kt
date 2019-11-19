package pl.reconizer.unfold.presentation.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import androidx.core.content.ContextCompat
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

// Fake LocationProvider in order to detach location updates from actual gps.
// For convenient testing.
class DebugLocationProvider(private val context: Context) : ILocationProvider, LocationListener {

    private var previousLocation: Location? = null

    override var lastLocation: Location? = null
        private set

    override val statusChange: PublishSubject<GpsInterfaceStatus> = PublishSubject.create<GpsInterfaceStatus>()
    override val locationChange: PublishSubject<Location> = PublishSubject.create<Location>()
    override val lastLocationChange: BehaviorSubject<Location> = BehaviorSubject.create()

    override var isEnabled = false
        private set

    override val interfaceStatus: GpsInterfaceStatus
        get() = GpsInterfaceStatus.UP

    override val canCollectLocation: Boolean
        get() = hasPermission && interfaceStatus == GpsInterfaceStatus.UP && lastLocation != null

    override val hasPermission: Boolean
        get() = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    override fun enable() {
        if (isEnabled) {
            Timber.e("LocationProvider has been already enabled.")
            return
        }
        enableLocationChangeListening()
    }

    override fun disable() {
        Timber.d("stop location updates")
        isEnabled = false
    }

    override fun onLocationChanged(location: Location) {
        Timber.i("Location: (${location.latitude} | ${location.longitude})")
        previousLocation = lastLocation
        lastLocation = location
        locationChange.onNext(location)
        lastLocationChange.onNext(location)
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

    override fun onProviderEnabled(provider: String) {}

    override fun onProviderDisabled(provider: String) {}

    @SuppressLint("MissingPermission")
    private fun enableLocationChangeListening() {
        Timber.d("start_location_updates")
        if (lastLocation == null) {
            lastLocation = Location("").apply {
                latitude = DEFAULT_LAT
                longitude = DEFAULT_LNG
            }
        }
        lastLocationChange.onNext(lastLocation!!)
        isEnabled = true
    }

    companion object {
        const val DEFAULT_LAT = 53.018906
        const val DEFAULT_LNG = 18.603529
    }
}