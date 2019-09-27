package pl.reconizer.unfold.presentation.puzzle.cypherlock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_cypher_lock.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.domain.entities.puzzles.PuzzleType
import pl.reconizer.unfold.presentation.customviews.dialogs.PuzzleTutorialDialog
import pl.reconizer.unfold.presentation.puzzle.BasePuzzleFragment

class CypherLockPuzzleFragment : BasePuzzleFragment() {

    private val tutorialDialog = PuzzleTutorialDialog().apply {
        contentLayoutResId = R.layout.view_cypher_lock_tutorial
        headerTextResId = R.string.puzzle_number_lock_tutorial_title
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cypher_lock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        closeButton.setOnClickListener { navigator.goBack() }
        helpButton.setOnClickListener {
            tutorialDialog.show(childFragmentManager, "number_push_lock_tutorial")
        }

        confirmButton.setOnClickListener {
            presenter.resolvePoint(cypherLock.valuesStack.joinToString(""))
        }

        resetButton.setOnClickListener {
            cypherLock.resetLock()
        }

        cypherLock.numberOfRows = when(presenter.puzzleType) {
            PuzzleType.NUMBER_LOCK_4 -> 4
            PuzzleType.NUMBER_LOCK_5 -> 5
            PuzzleType.NUMBER_LOCK_6 -> 6
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