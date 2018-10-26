package pl.reconizer.cityadventure.presentation.map

import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import pl.reconizer.cityadventure.R
import pl.reconizer.cityadventure.domain.entities.Adventure

class AdventurePinMapper(
        private val provider: PinProvider
) {

    fun determinePin(adventure: Adventure): BitmapDescriptor {
        return if (adventure.purchasable) {
            when {
                adventure.completed -> provider.purchasableFinishedPin
                adventure.started -> provider.purchasableStartedPin
                else -> provider.purchasablePin
            }
        } else {
            when {
                adventure.completed -> provider.freeFinishedPin
                adventure.started -> provider.freeStartedPin
                else -> provider.freePin
            }
        }
    }

}