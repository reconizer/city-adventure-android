package pl.reconizer.cityadventure.common.extensions

import android.location.Location
import com.google.android.gms.maps.model.LatLng

fun Location.toLatLng(): LatLng {
    return LatLng(latitude, longitude)
}