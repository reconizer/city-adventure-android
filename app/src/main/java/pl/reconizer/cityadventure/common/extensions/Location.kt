package pl.reconizer.cityadventure.common.extensions

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import pl.reconizer.cityadventure.domain.entities.Position

fun Location.toLatLng(): LatLng {
    return LatLng(latitude, longitude)
}

fun Location.toPosition(): Position {
    return Position(latitude, longitude)
}