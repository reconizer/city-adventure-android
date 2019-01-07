package pl.reconizer.cityadventure.presentation.map

import com.google.android.gms.maps.model.BitmapDescriptor
import pl.reconizer.cityadventure.domain.entities.AdventurePoint

class StartedAdventurePinMapper(
        private val provider: PinProvider
): IPinMapper {

    override fun determinePin(t: Any): BitmapDescriptor? {
        return when(t) {
            is AdventurePoint -> {
                when {
                    t.isCompleted -> provider.freeFinishedPin
                    else -> provider.freeStartedPin
                }
            }
            else -> null
        }
    }

}