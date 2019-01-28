package pl.reconizer.cityadventure.presentation.navigation

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.presentation.adventure.startpoint.StartPointFragment

@Parcelize
class AdventureStartPointKey(
        val adventure: Adventure
) : BaseKey(
        bundleOf(
                StartPointFragment.ADVENTURE_PARAM to adventure
        )
) {

    override fun createFragment(): Fragment {
        return StartPointFragment()
    }
}