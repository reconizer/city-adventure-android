package pl.reconizer.cityadventure.presentation.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MapStyleOptions
import pl.reconizer.cityadventure.R

class MapFragment : SupportMapFragment(), IMapView {

    private var googleMap: GoogleMap? = null

    private var myLocationButton: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getMapAsync(this::configure)
    }

    override fun isMapReady(): Boolean {
        return googleMap != null
    }

    override fun goToMyLocation() {
        myLocationButton?.callOnClick()
    }

    @SuppressLint("MissingPermission") // TODO take care of permission
    private fun configure(map: GoogleMap) {
        googleMap = map
        if (googleMap!!.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.google_map_style))) {
            googleMap!!.apply {
                setMinZoomPreference(MIN_ZOOM)
                setMaxZoomPreference(MAX_ZOOM)
                isMyLocationEnabled = true
                isBuildingsEnabled = false
                isTrafficEnabled = false
                isIndoorEnabled = false

                uiSettings.apply {
                    isTiltGesturesEnabled = false
                    isZoomGesturesEnabled = true
                    isScrollGesturesEnabled = true
                    isRotateGesturesEnabled = true
                    isIndoorLevelPickerEnabled = false
                    isCompassEnabled = false
                    isZoomControlsEnabled = false
                    isMapToolbarEnabled = false
                    isMyLocationButtonEnabled = true
                }
            }

            // Android <3
            myLocationButton = (view?.findViewById<View>(Integer.parseInt("1"))?.parent as View?)?.findViewById(Integer.parseInt("2"))
            myLocationButton?.visibility = View.INVISIBLE
        } else {
            Log.e(TAG, "Map style parsing failed")
        }
    }

    companion object {
        const val TAG = "MapFragment"

        const val MIN_ZOOM = 15f
        const val MAX_ZOOM = 18f
    }

}