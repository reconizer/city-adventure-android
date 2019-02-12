package pl.reconizer.cityadventure.presentation.navigation.keys.puzzles

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.entities.AdventurePoint
import pl.reconizer.cityadventure.domain.entities.PuzzleType
import pl.reconizer.cityadventure.presentation.navigation.keys.BaseKey
import pl.reconizer.cityadventure.presentation.puzzle.BasePuzzleFragment
import pl.reconizer.cityadventure.presentation.puzzle.numberpushlock.NumberPushLockPuzzleFragment

@Parcelize
class NumberPushLockPuzzleKey(
        val adventure: Adventure,
        val adventurePoint: AdventurePoint,
        val puzzleType: PuzzleType
) : BaseKey(
        bundleOf(
                BasePuzzleFragment.ADVENTURE_PARAM to adventure,
                BasePuzzleFragment.ADVENTURE_POINT_PARAM to adventurePoint,
                BasePuzzleFragment.PUZZLE_TYPE_PARAM to puzzleType
        )
) {

    override fun createFragment(): Fragment {
        return NumberPushLockPuzzleFragment()
    }
}