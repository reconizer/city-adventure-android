package pl.reconizer.unfold.presentation.puzzle.numberpushlock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_number_push_lock.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.domain.entities.PuzzleType
import pl.reconizer.unfold.presentation.customviews.dialogs.PuzzleTutorialDialog
import pl.reconizer.unfold.presentation.puzzle.BasePuzzleFragment

class NumberPushLockPuzzleFragment : BasePuzzleFragment() {

    private val tutorialDialog = PuzzleTutorialDialog().apply {
        contentLayoutResId = R.layout.view_number_push_lock_tutorial
        headerTextResId = R.string.puzzle_number_push_lock_tutorial_title
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_number_push_lock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        closeButton.setOnClickListener { navigator.goBack() }
        helpButton.setOnClickListener {
            tutorialDialog.show(childFragmentManager, "number_push_lock_tutorial")
        }
        resetButton.setOnClickListener {
            pushLock?.clearSelection()
        }
        confirmButton.setOnClickListener {
            presenter.resolvePoint(pushLock.valuesStack.joinToString(""))
        }

        pushLock.numberOfRows = when(presenter.puzzleType) {
            PuzzleType.NUMBER_PUSH_LOCK_3 -> 3
            PuzzleType.NUMBER_PUSH_LOCK_4 -> 4
            PuzzleType.NUMBER_PUSH_LOCK_5 -> 5
            else -> 5
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