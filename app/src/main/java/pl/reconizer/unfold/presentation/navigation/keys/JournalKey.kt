package pl.reconizer.unfold.presentation.navigation.keys

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import pl.reconizer.unfold.domain.entities.Adventure
import pl.reconizer.unfold.domain.entities.AdventureStartPoint
import pl.reconizer.unfold.presentation.adventure.journal.JournalFragment

@Parcelize
class JournalKey(
        val adventure: Adventure,
        val adventureStartPoint: AdventureStartPoint
) : BaseKey(
        bundleOf(
                JournalFragment.ADVENTURE_PARAM to adventure,
                JournalFragment.ADVENTURE_START_POINT_PARAM to adventureStartPoint
        )
) {

    override fun createFragment(): Fragment {
        return JournalFragment()
    }
}