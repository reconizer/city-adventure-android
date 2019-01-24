package pl.reconizer.cityadventure.presentation.adventure.summary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import kotlinx.android.synthetic.main.fragment_adventure_summary.*
import pl.reconizer.cityadventure.R
import pl.reconizer.cityadventure.di.Injector
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.presentation.common.BaseFragment
import pl.reconizer.cityadventure.presentation.map.MapMode
import pl.reconizer.cityadventure.presentation.map.game.GameMapFragment.Companion.MAP_MODE_PARAM
import javax.inject.Inject

class AdventureSummaryFragment : BaseFragment(), IAdventureSummaryView {

    @Inject
    lateinit var presenter: AdventureSummaryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.buildAdventureSummaryComponent(arguments?.get(ADVENTURE_PARAM) as Adventure).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_adventure_summary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exitButton.setOnClickListener {
            navigator.openMapRoot(bundleOf(
                    MAP_MODE_PARAM to MapMode.ADVENTURES
            ))
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe(this)
        presenter.fetchUserRanking()
    }

    override fun onDestroy() {
        super.onDestroy()
        Injector.clearAdventureSummaryComponent()
    }

    override fun showUserRanking() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSummary() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        const val ADVENTURE_PARAM = "adventure"

        fun newInstance(adventure: Adventure): AdventureSummaryFragment {
            return AdventureSummaryFragment().apply {
                arguments = bundleOf(
                        ADVENTURE_PARAM to adventure
                )
            }
        }

    }

}