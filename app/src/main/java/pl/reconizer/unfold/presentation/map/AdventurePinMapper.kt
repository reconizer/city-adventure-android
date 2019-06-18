package pl.reconizer.unfold.presentation.map

import com.google.android.gms.maps.model.BitmapDescriptor
import pl.reconizer.unfold.domain.entities.MapAdventure

class AdventurePinMapper(
        private val provider: PinProvider
): IPinMapper {

    override fun determinePin(t: Any): BitmapDescriptor? {
        return when(t) {
            is MapAdventure -> {
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