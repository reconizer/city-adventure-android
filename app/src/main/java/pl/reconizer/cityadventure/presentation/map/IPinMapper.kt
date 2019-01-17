package pl.reconizer.cityadventure.presentation.map

import com.google.android.gms.maps.model.BitmapDescriptor

interface IPinMapper {

    fun determinePin(t: Any): BitmapDescriptor?

}