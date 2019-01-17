package pl.reconizer.cityadventure.presentation.map

import com.google.android.gms.maps.model.BitmapDescriptor
import pl.reconizer.cityadventure.domain.entities.Adventure

class AdventurePinMapper(
        private val provider: PinProvider
): IPinMapper {

    override fun determinePin(t: Any): BitmapDescriptor? {
        return when(t) {
            is Adventure -> {
                if (t.purchasable) {
                    when {
                        t.completed -> provider.purchasableFinishedPin
                        t.started -> provider.purchasableStartedPin
                        else -> provider.purchasablePin
                    }
                } else {
                    when {
                        t.completed -> provider.freeFinishedPin
                        t.started -> provider.freeStartedPin
                        else -> provider.freePin
                    }
                }
            }
            else -> null
        }
    }

}