package pl.reconizer.cityadventure.presentation.puzzle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import kotlinx.android.synthetic.main.fragment_text_puzzle.*
import pl.reconizer.cityadventure.R
import pl.reconizer.cityadventure.di.Injector
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.entities.AdventurePoint
import pl.reconizer.cityadventure.presentation.common.BaseFragment
import javax.inject.Inject

class TextPuzzleFragment : BaseFragment(), IPuzzleView {

    @Inject
    lateinit var presenter: PuzzlePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adventure = arguments?.get(ADVENTURE_PARAM) as Adventure?
        val adventurePoint = arguments?.get(ADVENTURE_POINT_PARAM) as AdventurePoint?
        Injector.buildPuzzleComponent(
            adventure!!,
            adventurePoint!!
        ).inject(this)
    }

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

        }
        answerInput.requestFocus()
    }

    companion object {
        const val ADVENTURE_PARAM = "adventure"
        const val ADVENTURE_POINT_PARAM = "adventure_point"

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