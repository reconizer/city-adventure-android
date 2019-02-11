package pl.reconizer.cityadventure.presentation.puzzle.numberpushlock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import kotlinx.android.synthetic.main.fragment_number_push_lock.*
import pl.reconizer.cityadventure.R
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.entities.AdventurePoint
import pl.reconizer.cityadventure.presentation.puzzle.BasePuzzleFragment

class NumberPushLockPuzzleFragment : BasePuzzleFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_number_push_lock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        closeButton.setOnClickListener { navigator.goBack() }
//        resetButton.setOnClickListener {
//            answerInput.setText("")
//        }
//        confirmButton.setOnClickListener {
//            presenter.resolvePoint(answerInput.text.toString())
//        }
//        answerInput.requestFocus()

        pushLock.numberOfRows = 4
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }

    companion object {

        fun newInstance(adventure: Adventure, adventurePoint: AdventurePoint): NumberPushLockPuzzleFragment {
            return NumberPushLockPuzzleFragment().apply {
                arguments = bundleOf(
                        ADVENTURE_PARAM to adventure,
                        ADVENTURE_POINT_PARAM to adventurePoint
                )
            }
        }
    }

}