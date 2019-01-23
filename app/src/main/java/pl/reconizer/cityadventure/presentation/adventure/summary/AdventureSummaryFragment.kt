package pl.reconizer.cityadventure.presentation.adventure.summary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pl.reconizer.cityadventure.R
import pl.reconizer.cityadventure.presentation.common.BaseFragment

class AdventureSummaryFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_adventure_summary, container, false)
    }

    companion object {

        fun newInstance(): AdventureSummaryFragment {
            return AdventureSummaryFragment()
        }

    }

}