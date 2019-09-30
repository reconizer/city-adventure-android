package pl.reconizer.unfold.presentation.navigation.keys.puzzles

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import pl.reconizer.unfold.domain.entities.MapAdventure
import pl.reconizer.unfold.domain.entities.AdventurePoint
import pl.reconizer.unfold.domain.entities.puzzles.PuzzleType
import pl.reconizer.unfold.presentation.navigation.keys.BaseKey
import pl.reconizer.unfold.presentation.puzzle.BasePuzzleFragment
import pl.reconizer.unfold.presentation.puzzle.numberpushlock.NumberPushLockPuzzleFragment

@Parcelize
class NumberPushLockPuzzleKey(
        val adventure: MapAdventure,
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