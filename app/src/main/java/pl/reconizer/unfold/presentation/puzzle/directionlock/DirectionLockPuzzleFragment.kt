package pl.reconizer.unfold.presentation.puzzle.directionlock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_direction_lock.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.common.extensions.isFragmentOnStack
import pl.reconizer.unfold.common.extensions.performOneShotVibration
import pl.reconizer.unfold.domain.entities.puzzles.PuzzleType
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
            if (!childFragmentManager.isFragmentOnStack(TUTORIAL_DIALOG_TAG)) {
                tutorialDialog.show(childFragmentManager, TUTORIAL_DIALOG_TAG)
            }
        }

        directionLockAnswers.numberOfItems = when (presenter.puzzleType) {
            PuzzleType.DIRECTION_LOCK_4 -> 4
            PuzzleType.DIRECTION_LOCK_6 -> 6
            PuzzleType.DIRECTION_LOCK_8 -> 8
            else -> 4
        }

        directionLock.onNewDirectionListener = { newDirection ->
            var answers = directionLockAnswers.answers.filterNotNull()
            val nextPosition = answers.size
            if (nextPosition < directionLockAnswers.numberOfItems) {
                directionLockAnswers.addAnswer(nextPosition, newDirection)

                answers = directionLockAnswers.answers.filterNotNull()
                if (answers.size == directionLockAnswers.numberOfItems) { // full lock
                    presenter.resolvePoint(answers.joinToString("") { it.code })
                }
            }
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
        directionLock.reset()
        directionLockAnswers.reset()
        context?.performOneShotVibration(400)
    }

    companion object {
        private const val TUTORIAL_DIALOG_TAG = "direction_lock_tutorial"
    }

}