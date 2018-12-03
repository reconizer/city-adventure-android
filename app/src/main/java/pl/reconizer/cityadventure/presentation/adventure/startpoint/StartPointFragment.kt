package pl.reconizer.cityadventure.presentation.adventure.startpoint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import com.bartoszlipinski.viewpropertyobjectanimator.ViewPropertyObjectAnimator
import kotlinx.android.synthetic.main.fragment_adventure_start_point.*
import kotlinx.android.synthetic.main.view_adventure_start_point_rating.*
import kotlinx.android.synthetic.main.view_adventure_start_point_ranking.*
import kotlinx.android.synthetic.main.view_adventure_start_point_top_ranking.*
import pl.reconizer.cityadventure.R
import pl.reconizer.cityadventure.common.extensions.TimeConsts
import pl.reconizer.cityadventure.di.Injector
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.entities.AdventureStartPoint
import pl.reconizer.cityadventure.presentation.common.BaseFragment
import pl.reconizer.cityadventure.presentation.customviews.ShadowGenerator
import pl.reconizer.cityadventure.presentation.gallery.GalleryFragment
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

        ratingViewContainer.isGone = true
        difficultyLevel.isGone = true
        timeLength.isGone = true
        galleryPreview.isGone = true

        ratingView.rateListener = {
            showRating()
        }

        closeButton.setOnClickListener {
            navigator.goBack()
        }

        updateActionButton()

        authorInfo.shadowGenerator = shadowGenerator
        galleryPreview.shadowGenerator = shadowGenerator
        userRankingView.shadowGenerator = shadowGenerator
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe(this)
        if (presenter.adventureStartPoint != null) {
            show(presenter.adventureStartPoint!!)
        }
        presenter.fetchData()
    }

    override fun onDestroy() {
        super.onDestroy()
        Injector.clearAdventureStartPointComponent()
    }

    override fun show(adventureStartPoint: AdventureStartPoint) {
        showBanner(adventureStartPoint)
        showAuthor(adventureStartPoint)
        showFinishTime(adventureStartPoint)
        showGallery(adventureStartPoint)
        showCurrentUserRanking(adventureStartPoint)
        showTopFiveRanking(adventureStartPoint)
        showCurrentUserRating(adventureStartPoint)

        difficultyLevel.isGone = false
        difficultyLevel.level = adventureStartPoint.difficultyLevel

        adventureDescription.text = adventureStartPoint.description
    }

    private fun showBanner(adventureStartPoint: AdventureStartPoint) {
        banner.setImage(adventureStartPoint.coverImage)
        banner.name = adventureStartPoint.name
        banner.rating = adventureStartPoint.rating
        banner.ratingCounter = adventureStartPoint.ratingCount
    }

    private fun showAuthor(adventureStartPoint: AdventureStartPoint) {
        authorInfo.name = adventureStartPoint.authorName
        authorInfo.setLogo(adventureStartPoint.authorImage)
    }

    private fun showFinishTime(adventureStartPoint: AdventureStartPoint) {
        if (adventureStartPoint.minFinishTime == null || adventureStartPoint.maxFinishTime == null) {
            timeLength.isGone = true
        } else {
            timeLength.minLength = adventureStartPoint.minFinishTime
            timeLength.maxLength = adventureStartPoint.maxFinishTime
            timeLength.isGone = false
        }
    }

    private fun showGallery(adventureStartPoint: AdventureStartPoint) {
        if (adventureStartPoint.gallery.isEmpty()) {
            galleryPreview.isGone = true
        } else {
            galleryPreview.isGone = false
            galleryPreview.setImages(adventureStartPoint.gallery)
            galleryPreview.thumbClickListener = {idx, _ ->
                navigator.openOver(GalleryFragment.newInstance(adventureStartPoint.gallery, idx))
            }
        }
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
                setAvatar(R.drawable.test_avatar)
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
                    setAvatar(R.drawable.test_avatar)
                }
            }
        }
    }

    private fun showRating() {
        ratingStamp.isGone = false
        ratingStamp.rating = ratingView.rating
        ViewPropertyObjectAnimator
                .animate(ratingViewContainer)
                .withLayer()
                .alpha(0f)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .setDuration(RATING_VISIBILITY_ANIMATION_DURATION)
                .setStartDelay(RATING_ANIMATION_DELAY)
                .start()
        ViewPropertyObjectAnimator
                .animate(ratingViewContainer)
                .withLayer()
                .height(0)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .setDuration(RATING_HEIGHT_ANIMATION_DURATION)
                .setStartDelay(RATING_VISIBILITY_ANIMATION_DURATION + RATING_ANIMATION_DELAY)
                .withEndAction { ratingViewContainer.isGone = true }
                .start()
        showRatingStamp()
    }

    private fun showCurrentUserRating(adventureStartPoint: AdventureStartPoint) {
        if (adventure?.completed == true) {
            ratingViewContainer.isGone = adventureStartPoint.currentUserRating != null
            ratingStamp.isGone = adventureStartPoint.currentUserRating == null
            ratingStamp.rating = adventureStartPoint.currentUserRating ?: 0
        } else {
            ratingViewContainer.isGone = true
            ratingStamp.isGone = true
        }
    }

    private fun showRatingStamp() {
        val scale = 1.5f
        ratingStamp.scaleX = scale
        ratingStamp.scaleY = scale
        ViewPropertyObjectAnimator
                .animate(ratingStamp)
                .scales(1f)
                .alpha(1f)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .setDuration(RATING_STAMP_ANIMATION_DURATION)
                .setStartDelay(RATING_VISIBILITY_ANIMATION_DURATION + RATING_HEIGHT_ANIMATION_DURATION + RATING_ANIMATION_DELAY)
                .start()
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
    }

    companion object {
        const val ADVENTURE_PARAM = "adventure"
        const val RATING_ANIMATION_DELAY = 300L
        const val RATING_VISIBILITY_ANIMATION_DURATION = 200L
        const val RATING_HEIGHT_ANIMATION_DURATION = 200L
        const val RATING_STAMP_ANIMATION_DURATION = 500L

        fun newInstance(adventure: Adventure): StartPointFragment {
            return StartPointFragment().apply {
                arguments = bundleOf(ADVENTURE_PARAM to adventure)
            }
        }

    }

}