package pl.reconizer.cityadventure.presentation.map

import android.graphics.Bitmap
import com.google.android.gms.maps.model.LatLng

interface IMapView {

    var overlayBitmap: Bitmap

    fun isMapReady(): Boolean
    fun moveToLocation(location: LatLng)
    fun handleNewUserLocation(location: LatLng)

}