package pl.reconizer.unfold.presentation.map

import com.google.android.gms.maps.model.BitmapDescriptor

class UserPinMapper(
        private val provider: PinProvider
): IPinMapper {

    override fun determinePin(t: Any?): BitmapDescriptor? {
        return provider.userPin
    }

}