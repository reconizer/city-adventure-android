package pl.reconizer.unfold.presentation.adventure.summary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_adventure_summary.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.di.Injector
import pl.reconizer.unfold.domain.entities.MapAdventure
import pl.reconizer.unfold.presentation.adventure.ranking.RankingAdapter
import pl.reconizer.unfold.presentation.common.BaseFragment
import pl.reconizer.unfold.presentation.common.recyclerview.ItemOffsetDecorator
import javax.inject.Inject

class AdventureSummaryFragment : BaseFragment(), IAdventureSummaryView {

    @Inject
    lateinit var presenter: AdventureSummaryPresenter

    @Inject
    lateinit var rankingAdapter: RankingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.buildAdventureSummaryComponent(arguments?.get(ADVENTURE_PARAM) as MapAdventure).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_adventure_summary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rankingRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rankingAdapter
            addItemDecoration(ItemOffsetDecorator(context, R.dimen.space_1x))
        }

        ratingView.rateListener = { rating ->
            presenter.rateAdventure(rating)
        }

        exitButton.setOnClickListener {
            navigator.jumpToRoot()
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe(this)
        view?.postDelayed({
            presenter.fetchData()
        }, resources.getInteger(R.integer.transitionDuration).toLong())

    }

    override fun onDestroy() {
        super.onDestroy()
        Injector.clearAdventureSummaryComponent()
    }

    override fun goBack(): Boolean {
        navigator.jumpToRoot()
        return true
    }

    override fun showUserRanking() {
        presenter.userRanking?.let {
            userRankingEntryView.apply {
                username = it.nick
                position = it.position
                completionTime = it.completionTime
                setAvatar(it.avatarUrl)
            }
        }
    }

    override fun showSummary() {
        rankingAdapter.ranking = presenter.summary
        rankingAdapter.notifyDataSetChanged()
    }

    companion object {
        const val ADVENTURE_PARAM = "adventure"

        fun newInstance(adventure: MapAdventure): AdventureSummaryFragment {
            return AdventureSummaryFragment().apply {
                arguments = bundleOf(
                        ADVENTURE_PARAM to adventure
                )
            }
        }

    }

}