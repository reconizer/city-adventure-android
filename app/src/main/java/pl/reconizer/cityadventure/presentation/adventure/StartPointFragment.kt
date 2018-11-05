package pl.reconizer.cityadventure.presentation.adventure

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import kotlinx.android.synthetic.main.fragment_adventure_start_point.*
import pl.reconizer.cityadventure.R
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.presentation.common.BaseFragment

class StartPointFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_adventure_start_point, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView.text = (arguments!![ADVENTURE_PARAM] as Adventure).adventureId
    }

    companion object {
        const val ADVENTURE_PARAM = "adventure"

        fun newInstance(adventure: Adventure): StartPointFragment {
            return StartPointFragment().apply {
                arguments = bundleOf(ADVENTURE_PARAM to adventure)
            }
        }

    }

}