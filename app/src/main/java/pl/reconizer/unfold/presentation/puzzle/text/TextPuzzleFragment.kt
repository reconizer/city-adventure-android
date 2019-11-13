package pl.reconizer.unfold.presentation.puzzle.text

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_text_puzzle.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.presentation.puzzle.BasePuzzleFragment

class TextPuzzleFragment : BasePuzzleFragment() {

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

}