package pl.reconizer.cityadventure.presentation.puzzle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import kotlinx.android.synthetic.main.fragment_text_puzzle.*
import pl.reconizer.cityadventure.R
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.entities.AdventurePoint
import pl.reconizer.cityadventure.presentation.navigation.keys.AdventureSummaryKey

class TextPuzzleFragment : BasePuzzleFragment(), IPuzzleView {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_text_puzzle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        closeButton.setOnClickListener { navigator.goBack() }
        resetButton.setOnClickListener {
            answerInput.setText("")
        }
        confirmButton.setOnClickListener {
            presenter.resolvePoint(answerInput.text.toString())
        }
        answerInput.requestFocus()
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }

    override fun correctAnswer() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun wrongAnswer() {
        showWrongAnswerDialog()
    }

    override fun completedAdventure() {
        navigator.goTo(AdventureSummaryKey(
                arguments?.get(ADVENTURE_PARAM) as Adventure
        ))
    }

    companion object {

        fun newInstance(adventure: Adventure, adventurePoint: AdventurePoint): TextPuzzleFragment {
            return TextPuzzleFragment().apply {
                arguments = bundleOf(
                        ADVENTURE_PARAM to adventure,
                        ADVENTURE_POINT_PARAM to adventurePoint
                )
            }
        }
    }

}