package pl.reconizer.unfold.presentation.puzzle.directionlock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_direction_lock.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.presentation.customviews.dialogs.PuzzleTutorialDialog
import pl.reconizer.unfold.presentation.puzzle.BasePuzzleFragment

class DirectionLockPuzzleFragment : BasePuzzleFragment() {

    private val tutorialDialog = PuzzleTutorialDialog().apply {
        contentLayoutResId = R.layout.view_direction_lock_tutorial
        headerTextResId = R.string.puzzle_direction_lock_tutorial_title
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_direction_lock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        closeButton.setOnClickListener { navigator.goBack() }
        helpButton.setOnClickListener {
            tutorialDialog.show(childFragmentManager, "direction_lock_tutorial")
        }

        confirmButton.setOnClickListener {
            presenter.resolvePoint(directionLock.valuesStack.joinToString("") { it.code })
        }

        resetButton.setOnClickListener {
            directionLock.resetLock()
            Toast.makeText(context, R.string.puzzle_direction_lock_reset, Toast.LENGTH_SHORT).show()
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

    override fun wrongAnswer() {
        super.wrongAnswer()
        directionLock.resetLock()
    }

}