package pl.reconizer.cityadventure.presentation.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.maps.android.SphericalUtil
import io.reactivex.subjects.PublishSubject
import pl.reconizer.cityadventure.common.extensions.toLatLng

class LocationProvider(private val context: Context) : ILocationProvider, LocationListener {

    private var locationManager: LocationManager? = null
    private var locationProvider: String? = null
    private var previousLocation: Location? = null

    override var lastLocation: Location? = null
        private set

    override val changeDistance: Double
        get() {
            return if (lastLocation == null || previousLocation == null) {
                0.0
            } else {
                SphericalUtil.computeDistanceBetween(
                        lastLocation!!.toLatLng(),
                        previousLocation!!.toLatLng()
                )
            }
        }

    override val statusChange: PublishSubject<GpsInterfaceStatus> = PublishSubject.create<GpsInterfaceStatus>()
    override val locationChange: PublishSubject<Location> = PublishSubject.create<Location>()

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
            Log.e(TAG, "LocationProvider has been already enabled.")
            return
        }
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        enableLocationChangeListening()
    }

    override fun disable() {
        Log.d(TAG, "stop_location_updates")
        locationManager?.removeUpdates(this)
        isEnabled = false
    }

    override fun onLocationChanged(location: Location) {
        previousLocation = lastLocation
        lastLocation = location
        locationChange.onNext(location)
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
        when (status) {
            android.location.LocationProvider.AVAILABLE -> {
                Log.d(TAG, "location_provider_available: $provider")
            }
            android.location.LocationProvider.OUT_OF_SERVICE -> {
                Log.d(TAG, "location_provider_out_of_service: $provider")
            }
            android.location.LocationProvider.TEMPORARILY_UNAVAILABLE -> {
                Log.d(TAG, "location_provider_unavailable: $provider")
            }
        }
    }

    override fun onProviderEnabled(provider: String) {
        Log.d(TAG, "location_provider_enabled: $provider")
        statusChange.onNext(interfaceStatus)
    }

    override fun onProviderDisabled(provider: String) {
        Log.d(TAG, "location_provider_disabled: $provider")
        statusChange.onNext(interfaceStatus)
    }

    @SuppressLint("MissingPermission")
    private fun enableLocationChangeListening() {
        val criteria = Criteria()
        locationProvider = locationManager!!.getBestProvider(criteria, false)

        if (locationProvider != null && hasPermission) {
            locationManager!!.requestLocationUpdates(
                    locationProvider,
                    MIN_TIME_FOR_LOCATION_UPDATE,
                    MIN_DISTANCE_FOR_LOCATION_UPDATE,
                    this
            )
            lastLocation = locationManager!!.getLastKnownLocation(locationProvider)

            Log.d(TAG, "start_location_updates")
            isEnabled = true
        }
    }

    companion object {
        const val TAG = "LocationProvider"
        const val MIN_TIME_FOR_LOCATION_UPDATE = 400L
        const val MIN_DISTANCE_FOR_LOCATION_UPDATE = 1f
    }
}