package pl.reconizer.cityadventure.presentation.navigation.keys

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.entities.AdventurePoint
import pl.reconizer.cityadventure.presentation.puzzle.BasePuzzleFragment
import pl.reconizer.cityadventure.presentation.puzzle.text.TextPuzzleFragment

@Parcelize
class TextPuzzleKey(
        val adventure: Adventure,
        val adventurePoint: AdventurePoint
) : BaseKey(
        bundleOf(
                BasePuzzleFragment.ADVENTURE_PARAM to adventure,
                BasePuzzleFragment.ADVENTURE_POINT_PARAM to adventurePoint
        )
) {

    override fun createFragment(): Fragment {
        return TextPuzzleFragment()
    }
}