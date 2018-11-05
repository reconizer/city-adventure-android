package pl.reconizer.cityadventure.common.extensions

import com.google.android.gms.maps.model.LatLng
import pl.reconizer.cityadventure.domain.entities.Position

fun Position.toLatLng(): LatLng {
    return LatLng(lat, lng)
}