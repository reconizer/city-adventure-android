package pl.reconizer.cityadventure.presentation.map

import android.graphics.Bitmap
import com.google.android.gms.maps.model.LatLng
import pl.reconizer.cityadventure.domain.entities.Adventure

interface IMapView {

    var cameraMoveListener: ((cameraDetails: CameraDetails) -> Unit)?
    var cameraMovedListener: ((cameraDetails: CameraDetails) -> Unit)?

    var overlayBitmap: Bitmap

    fun isMapReady(): Boolean
    fun moveToLocation(location: LatLng)
    fun handleNewUserLocation(location: LatLng)

    fun showAdventureMarkers(adventures: List<Adventure>)

}