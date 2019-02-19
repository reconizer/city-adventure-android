package pl.reconizer.unfold.presentation.map

import com.google.android.gms.maps.model.BitmapDescriptor

interface IPinMapper {

    fun determinePin(t: Any): BitmapDescriptor?

}