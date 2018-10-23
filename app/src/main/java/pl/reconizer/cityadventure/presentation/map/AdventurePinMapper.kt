package pl.reconizer.cityadventure.presentation.map

import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import pl.reconizer.cityadventure.R
import pl.reconizer.cityadventure.domain.entities.Adventure

class AdventurePinMapper {

    private val purchasablePin by lazy { BitmapDescriptorFactory.fromResource(R.drawable.icon_pin_purchasable) }

    private val freePin by lazy { BitmapDescriptorFactory.fromResource(R.drawable.icon_pin_free_not_started) }
    private val freeStartedPin by lazy { BitmapDescriptorFactory.fromResource(R.drawable.icon_pin_free_started) }
    private val freeFinishedPin by lazy { BitmapDescriptorFactory.fromResource(R.drawable.icon_pin_free_finished) }

    fun determinePin(adventure: Adventure): BitmapDescriptor {
        return if (adventure.purchasable) {
            when {
                adventure.completed -> freeFinishedPin // we dont have a correct pin
                adventure.started -> freeStartedPin
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