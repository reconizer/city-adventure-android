package pl.reconizer.unfold.common.extensions

import com.google.android.gms.maps.model.LatLng
import pl.reconizer.unfold.domain.entities.Position

fun Position.toLatLng(): LatLng {
    return LatLng(lat, lng)
}