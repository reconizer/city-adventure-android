package pl.reconizer.cityadventure.presentation.map

import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import pl.reconizer.cityadventure.R
import pl.reconizer.cityadventure.domain.entities.Adventure

class AdventurePinMapper {

    val purchasablePin: BitmapDescriptor by lazy { BitmapDescriptorFactory.fromResource(R.drawable.icon_pin_purchasable_not_started) }
    val purchasableStartedPin: BitmapDescriptor by lazy { BitmapDescriptorFactory.fromResource(R.drawable.icon_pin_purchasable_started) }
    val purchasableFinishedPin: BitmapDescriptor by lazy { BitmapDescriptorFactory.fromResource(R.drawable.icon_pin_purchasable_finished) }

    val freePin: BitmapDescriptor by lazy { BitmapDescriptorFactory.fromResource(R.drawable.icon_pin_free_not_started) }
    val freeStartedPin: BitmapDescriptor by lazy { BitmapDescriptorFactory.fromResource(R.drawable.icon_pin_free_started) }
    val freeFinishedPin: BitmapDescriptor by lazy { BitmapDescriptorFactory.fromResource(R.drawable.icon_pin_free_finished) }

    fun determinePin(adventure: Adventure): BitmapDescriptor {
        return if (adventure.purchasable) {
            when {
                adventure.completed -> purchasableFinishedPin
                adventure.started -> purchasableStartedPin
                else -> purchasablePin
            }
        } else {
            when {
                adventure.completed -> freeFinishedPin
                adventure.started -> freeStartedPin
                else -> freePin
            }
        }
    }

}