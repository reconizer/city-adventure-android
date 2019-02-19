package pl.reconizer.unfold.presentation.adventure.startpoint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import com.squareup.picasso.Picasso
import com.zhuinden.simplestack.StateChange
import kotlinx.android.synthetic.main.fragment_adventure_start_point.*
import kotlinx.android.synthetic.main.view_adventure_start_point_ranking.*
import kotlinx.android.synthetic.main.view_adventure_start_point_top_ranking.*
import kotlinx.android.synthetic.main.view_ranking_title.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.di.Injector
import pl.reconizer.unfold.domain.entities.Adventure
import pl.reconizer.unfold.domain.entities.AdventureStartPoint
import pl.reconizer.unfold.presentation.common.BaseFragment
import pl.reconizer.unfold.presentation.customviews.ShadowGenerator
import pl.reconizer.unfold.presentation.navigation.keys.GalleryKey
import pl.reconizer.unfold.presentation.navigation.keys.JournalKey
import javax.inject.Inject

class StartPointFragment : BaseFragment(), IStartPointView {

    @Inject
    lateinit var presenter: StartPointPresenter

    @Inject
    lateinit var shadowGenerator: ShadowGenerator

    val adventure by lazy { arguments?.get(ADVENTURE_PARAM) as Adventure? }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.buildAdventureStartPointComponent(adventure!!).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_adventure_start_point, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // I'm hiding ranking section but showing adventure info section without data.
        rankingContainer.isGone = true

        adventureInfoView.galleryImageClickListener = { imageIndex ->
            presenter.adventureStartPoint?.let {
                navigator.goTo(GalleryKey(it.gallery, imageIndex))
            }
        }

        adventureInfoView.rateListener = { rateValue ->

        }

        adventureInfoView.shadowGenerator = shadowGenerator
        adventureInfoView.rateable = true
        adventureInfoView.isCompleted = adventure?.completed ?: false

        closeButton.setOnClickListener {
            goBack()
        }

        updateActionButton()

        userRankingView.shadowGenerator = shadowGenerator

        Picasso.get()
                .load(R.drawable.start_point_ranking_background)
                .into(rankingBackground)

        Picasso.get()
                .load(R.drawable.ranking_title_frame)
                .into(rankingTitleFrame)

        Picasso.get()
                .load(R.drawable.ranking_title)
                .into(rankingTitleText)

        Picasso.get()
                .load(R.drawable.start_point_ranking_top_outer_frame)
                .into(outerFrame)

        Picasso.get()
                .load(R.drawable.start_point_ranking_top_inner_frame)
                .into(innerFrame)
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe(this)
        if (presenter.adventureStartPoint != null) {
            show(presenter.adventureStartPoint!!)
        }
        view?.postDelayed({
            presenter.fetchData()
        }, resources.getInteger(R.integer.transitionDuration).toLong())
    }

    override fun onDestroy() {
        super.onDestroy()
        Injector.clearAdventureStartPointComponent()
    }

    override fun goBack(): Boolean {
        navigator.goBack()
        return true
    }

    override fun show(adventureStartPoint: AdventureStartPoint) {
        adventureInfoView.isGone = false
        rankingContainer.isGone = false
        adventureInfoView.adventureStartPoint = adventureStartPoint
        showCurrentUserRanking(adventureStartPoint)
        showTopFiveRanking(adventureStartPoint)
    }

    override fun adventureStarted() {
        goToJournal()
    }

    private fun showCurrentUserRanking(adventureStartPoint: AdventureStartPoint) {
        if (adventureStartPoint.currentUserRanking == null) {
            userRankingView.isGone = true
        } else {
            userRankingView.isGone = false
            userRankingView.apply {
                username = adventureStartPoint.currentUserRanking.nick
                position = adventureStartPoint.currentUserRanking.position
                completionTime = adventureStartPoint.currentUserRanking.completionTime
                setAvatar(adventureStartPoint.currentUserRanking.avatarUrl)
            }
        }

    }

    private fun showTopFiveRanking(adventureStartPoint: AdventureStartPoint) {
        listOf(
                firstPlaceEntryView,
                secondPlaceEntryView,
                thirdPlaceEntryView,
                fourthPlaceEntryView,
                fifthPlaceEntryView
        ).forEachIndexed { index, rankingEntryView ->
            val rankingEntryEntity = adventureStartPoint.topFiveRanking.getOrNull(index)
            if (rankingEntryEntity == null) {
                rankingEntryView.apply {
                    hideData()
                    position = index + 1
                }
            } else {
                rankingEntryView.apply {
                    showData()
                    position = rankingEntryEntity.position
                    username = rankingEntryEntity.nick
                    completionTime = rankingEntryEntity.completionTime
                    setAvatar(rankingEntryEntity.avatarUrl)
                }
            }
        }
    }

    private fun updateActionButton() {
        adventure?.let {
            if (it.completed) {
                actionButton.isGone = true
            } else {
                actionButton.isGone = false
                when {
                    it.started -> actionButton.setImageResource(R.drawable.button_adventure_resume)
                    it.purchasable && !it.purchased -> actionButton.setImageResource(R.drawable.button_adventure_buy)
                    else -> actionButton.setImageResource(R.drawable.button_adventure_start)
                }
            }
        }

        actionButton.setOnClickListener {
            when {
                presenter.adventure.started -> goToJournal()
                presenter.adventure.purchasable && presenter.adventure.purchased -> {
                    // TODO start buying process
                }
                else -> presenter.startAdventure()
            }
        }
    }

    private fun goToJournal() {
        if (adventure != null && presenter.adventureStartPoint != null) {
            navigator.replaceTop(
                    JournalKey(adventure!!, presenter.adventureStartPoint!!),
                    StateChange.REPLACE
            )
        }
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