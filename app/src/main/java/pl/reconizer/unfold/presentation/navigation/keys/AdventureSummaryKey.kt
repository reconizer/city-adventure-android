package pl.reconizer.unfold.presentation.navigation.keys

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import pl.reconizer.unfold.domain.entities.MapAdventure
import pl.reconizer.unfold.presentation.adventure.summary.AdventureSummaryFragment

@Parcelize
class AdventureSummaryKey(
        val adventure: MapAdventure
) : BaseKey(
        bundleOf(
                AdventureSummaryFragment.ADVENTURE_PARAM to adventure
        )
) {

    override fun createFragment(): Fragment {
        return AdventureSummaryFragment()
    }
}