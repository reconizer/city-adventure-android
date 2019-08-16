package pl.reconizer.unfold.presentation.navigation.keys

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import pl.reconizer.unfold.domain.entities.MapAdventure
import pl.reconizer.unfold.presentation.map.MapMode
import pl.reconizer.unfold.presentation.map.game.GameMapFragment
import pl.reconizer.unfold.presentation.map.game.GameMapFragment.Companion.ADVENTURE_PARAM
import pl.reconizer.unfold.presentation.map.game.GameMapFragment.Companion.ADVENTURE_POINT_ID_PARAM
import pl.reconizer.unfold.presentation.map.game.GameMapFragment.Companion.MAP_MODE_PARAM

@Parcelize
open class MapKey(
        val mapMode: MapMode,
        val adventure: MapAdventure? = null,
        val adventurePointId: String? = null
) : BaseKey(bundleOf(
                MAP_MODE_PARAM to mapMode,
                ADVENTURE_PARAM to adventure,
                ADVENTURE_POINT_ID_PARAM to adventurePointId
)) {

    override val isIdentifiedByTag: Boolean
        get() = true

    override val hasNewArguments: Boolean
        get() = true

    override val fragmentTag: String
        get() = "map"

    override fun createFragment(): Fragment {
        return GameMapFragment()
    }

    class Builder {
        companion object {
            fun buildAdventuresMapKey(): MapKey {
                return MapKey(MapMode.ADVENTURES)
            }

            fun buildAdventureMapKey(adventure: MapAdventure, adventurePointId: String? = null): MapKey {
                return MapKey(
                        MapMode.STARTED_ADVENTURE,
                        adventure,
                        adventurePointId
                )
            }
        }
    }

}