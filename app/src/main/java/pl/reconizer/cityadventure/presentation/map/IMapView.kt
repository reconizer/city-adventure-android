package pl.reconizer.cityadventure.presentation.map

import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import pl.reconizer.cityadventure.domain.entities.IPositionable

interface IMapView {

    var pinMapper: IPinMapper?
    var userPin: BitmapDescriptor?

    var pinClickListener: ((pin: IPositionable) -> Unit)?
    var cameraMoveListener: ((cameraDetails: CameraDetails) -> Unit)?
    var cameraMovedListener: ((cameraDetails: CameraDetails) -> Unit)?

    var overlayDrawableRes: Int

    fun isMapReady(): Boolean
    fun moveToLocation(location: LatLng)
    fun handleNewUserLocation(location: LatLng)

    fun showMarkers(positionables: List<IPositionable>)

}