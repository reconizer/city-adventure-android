package pl.reconizer.cityadventure.presentation.navigation.keys

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.presentation.adventure.summary.AdventureSummaryFragment

@Parcelize
class AdventureSummaryKey(
        val adventure: Adventure
) : BaseKey(
        bundleOf(
                AdventureSummaryFragment.ADVENTURE_PARAM to adventure
        )
) {

    override fun createFragment(): Fragment {
        return AdventureSummaryFragment()
    }
}