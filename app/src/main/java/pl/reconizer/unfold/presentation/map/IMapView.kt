package pl.reconizer.unfold.presentation.map

import com.google.android.gms.maps.model.LatLng
import pl.reconizer.unfold.domain.entities.IPositionable
import pl.reconizer.unfold.domain.entities.Position

interface IMapView {

    var pinMapper: IPinMapper?
    var userPinMapper: IPinMapper?

    var pinClickListener: ((pin: IPositionable) -> Unit)?
    var cameraMoveListener: ((cameraDetails: CameraDetails) -> Unit)?
    var cameraMovedListener: ((cameraDetails: CameraDetails) -> Unit)?

    var overlayDrawableRes: Int

    val isMapReady: Boolean

    fun moveToLocation(location: LatLng)
    fun handleNewUserLocation(location: LatLng)

    fun showMarkers(positionables: List<IPositionable>)
    fun clearMarkers()

    fun showMarkerRange(position: Position, range: Double)
    fun clearMarkerRange()

}