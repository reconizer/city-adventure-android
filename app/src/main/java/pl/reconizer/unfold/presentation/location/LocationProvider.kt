package pl.reconizer.unfold.presentation.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

class LocationProvider(private val context: Context) : ILocationProvider, LocationListener {

    private var locationManager: LocationManager? = null
    private var locationProvider: String? = null
    private var previousLocation: Location? = null

    override var lastLocation: Location? = null
        private set

    override val statusChange: PublishSubject<GpsInterfaceStatus> = PublishSubject.create<GpsInterfaceStatus>()
    override val locationChange: PublishSubject<Location> = PublishSubject.create<Location>()
    override val lastLocationChange: BehaviorSubject<Location> = BehaviorSubject.create()

    override var isEnabled = false
        private set

    override val interfaceStatus: GpsInterfaceStatus
        get() =
            if (locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true) {
                GpsInterfaceStatus.UP
            } else {
                GpsInterfaceStatus.DOWN
            }

    override val canCollectLocation: Boolean
        get() = hasPermission && interfaceStatus == GpsInterfaceStatus.UP && lastLocation != null

    override val hasPermission: Boolean
        get() = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    override fun enable() {
        if (isEnabled) {
            Timber.e("LocationProvider has been already enabled.")
            return
        }
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        enableLocationChangeListening()
    }

    override fun disable() {
        Timber.d("stop location updates")
        locationManager?.removeUpdates(this)
        isEnabled = false
    }

    override fun onLocationChanged(location: Location) {
        Timber.i("Location: (${location.latitude} | ${location.longitude})")
        previousLocation = lastLocation
        lastLocation = location
        locationChange.onNext(location)
        lastLocationChange.onNext(location)
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
        when (status) {
            android.location.LocationProvider.AVAILABLE -> {
                Timber.d("location_provider_available: $provider")
            }
            android.location.LocationProvider.OUT_OF_SERVICE -> {
                Timber.d("location_provider_out_of_service: $provider")
            }
            android.location.LocationProvider.TEMPORARILY_UNAVAILABLE -> {
                Timber.d("location_provider_unavailable: $provider")
            }
        }
    }

    override fun onProviderEnabled(provider: String) {
        Timber.d("location_provider_enabled: $provider")
        statusChange.onNext(interfaceStatus)
    }

    override fun onProviderDisabled(provider: String) {
        Timber.d("location_provider_disabled: $provider")
        statusChange.onNext(interfaceStatus)
    }

    @SuppressLint("MissingPermission")
    private fun enableLocationChangeListening() {
        val criteria = Criteria()
        locationProvider = locationManager!!.getBestProvider(criteria, false)

        if (locationProvider != null && hasPermission) {
            locationManager?.let {
                it.requestLocationUpdates(
                        locationProvider,
                        MIN_TIME_FOR_LOCATION_UPDATE,
                        MIN_DISTANCE_FOR_LOCATION_UPDATE,
                        this
                )
                lastLocation = it.getLastKnownLocation(locationProvider)
            }

            Timber.d("start_location_updates")
            isEnabled = true
        }
    }

    companion object {
        const val MIN_TIME_FOR_LOCATION_UPDATE = 400L // in mills
        const val MIN_DISTANCE_FOR_LOCATION_UPDATE = 1f // in meters
    }
}