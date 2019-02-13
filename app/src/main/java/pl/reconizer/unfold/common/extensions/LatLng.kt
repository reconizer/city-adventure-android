package pl.reconizer.unfold.common.extensions

import com.google.android.gms.maps.model.LatLng
import pl.reconizer.unfold.domain.entities.Position

fun LatLng.toPosition(): Position {
    return Position(latitude, longitude)
}