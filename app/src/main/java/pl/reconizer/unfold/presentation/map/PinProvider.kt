package pl.reconizer.unfold.presentation.map

import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import pl.reconizer.unfold.R

class PinProvider {

    val userPin: BitmapDescriptor by lazy { BitmapDescriptorFactory.fromResource(R.drawable.icon_pin_user) }

    val purchasablePin: BitmapDescriptor by lazy { BitmapDescriptorFactory.fromResource(R.drawable.icon_pin_purchasable_not_started) }
    val purchasableStartedPin: BitmapDescriptor by lazy { BitmapDescriptorFactory.fromResource(R.drawable.icon_pin_purchasable_started) }
    val purchasableFinishedPin: BitmapDescriptor by lazy { BitmapDescriptorFactory.fromResource(R.drawable.icon_pin_purchasable_finished) }

    val freePin: BitmapDescriptor by lazy { BitmapDescriptorFactory.fromResource(R.drawable.icon_pin_free_not_started) }
    val freeStartedPin: BitmapDescriptor by lazy { BitmapDescriptorFactory.fromResource(R.drawable.icon_pin_free_started) }
    val freeFinishedPin: BitmapDescriptor by lazy { BitmapDescriptorFactory.fromResource(R.drawable.icon_pin_free_finished) }

}