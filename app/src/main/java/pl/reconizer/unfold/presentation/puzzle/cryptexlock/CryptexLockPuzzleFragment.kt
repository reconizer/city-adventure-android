package pl.reconizer.unfold.presentation.puzzle.cryptexlock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_cryptex_lock.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.domain.entities.puzzles.PuzzleType
import pl.reconizer.unfold.presentation.customviews.dialogs.PuzzleTutorialDialog
import pl.reconizer.unfold.presentation.puzzle.BasePuzzleFragment

class CryptexLockPuzzleFragment : BasePuzzleFragment() {

    private val tutorialDialog = PuzzleTutorialDialog().apply {
        contentLayoutResId = R.layout.view_cryptex_lock_tutorial
        headerTextResId = R.string.puzzle_cryptex_lock_tutorial_title
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cryptex_lock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        closeButton.setOnClickListener { navigator.goBack() }
        helpButton.setOnClickListener {
            tutorialDialog.show(childFragmentManager, "cryptex_lock_tutorial")
        }

        confirmButton.setOnClickListener {
        }

        cryptexLock.numberOfCylinders = when(presenter.puzzleType) {
            PuzzleType.CRYPTEX_LOCK_4 -> 4
            PuzzleType.CRYPTEX_LOCK_5 -> 5
            PuzzleType.CRYPTEX_LOCK_6 -> 6
            PuzzleType.CRYPTEX_LOCK_7 -> 7
            else -> 4
        }

    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }

}