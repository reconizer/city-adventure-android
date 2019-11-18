package pl.reconizer.unfold.presentation.puzzle.text

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import kotlinx.android.synthetic.main.fragment_text_puzzle.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.common.extensions.hideKeyboard
import pl.reconizer.unfold.common.extensions.showKeyboard
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

        // Xml doesn't allow configure EditText to be a single line input
        // but with ability to break lines. Having xml's textMultiline set
        // and programmatically setting rawInputType a input starts behave like expected.
        answerInput.setRawInputType(InputType.TYPE_CLASS_TEXT)

        answerInput.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                presenter.resolvePoint(answerInput.text.toString())
                true
            } else {
                false
            }
        }

        answerInput.requestFocus()
        context?.showKeyboard(answerInput)
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
        context?.hideKeyboard(view)
    }

}