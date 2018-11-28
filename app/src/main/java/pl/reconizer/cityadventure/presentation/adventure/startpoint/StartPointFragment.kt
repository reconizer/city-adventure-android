package pl.reconizer.cityadventure.presentation.adventure.startpoint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.transition.Fade
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
import pl.reconizer.cityadventure.presentation.gallery.GalleryFragment
import javax.inject.Inject

class StartPointFragment : BaseFragment(), IStartPointView {

    @Inject
    lateinit var presenter: StartPointPresenter

//    val images = listOf(
//            "https://dummyimage.com/1440x800/0033ff/fff",
//            "https://dummyimage.com/600x800/ff001e/fff",
//            "https://dummyimage.com/1200x800/000/fff",
//            "https://dummyimage.com/960x800/0033ff/fff",
//            "https://dummyimage.com/1920x1080/ff8400/fff",
//            "https://dummyimage.com/1440x800/ff001e/fff",
//            "https://dummyimage.com/1440x800/000/0033ff",
//            "https://dummyimage.com/1440x800/000/ff001e",
//            "https://dummyimage.com/1440x800/000/fff",
//            "https://dummyimage.com/1440x800/000/fff"
//    )

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

//        banner.setImage("https://placekitten.com/g/1000/1600")
//        banner.name = "Some Cool Adventure"
//        banner.rating = 4.6f
//        banner.ratingCounter = 154

//        authorInfo.name = "Cool Author"
//        authorInfo.setLogo("https://placekitten.com/g/300/300")

//        difficultyLevelValue.level = DifficultyLevel.MEDIUM

//        timeLength.minLength = 10L * TimeConsts.SECONDS_IN_HOUR
//        timeLength.maxLength = 3L * TimeConsts.SECONDS_IN_DAY + 5 * TimeConsts.SECONDS_IN_HOUR + 43

//        adventureDescription.text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas leo magna, auctor et ornare a, auctor ac elit. Cras sit amet consectetur est. Suspendisse dui lacus, blandit non neque fermentum, tincidunt eleifend tortor. Phasellus faucibus volutpat sapien, at viverra turpis vestibulum eu. Donec iaculis ullamcorper dolor, a consectetur nulla. Donec eget semper neque. Donec interdum sagittis nisl. Etiam at metus posuere, tincidunt ligula a, tristique dui."

//        galleryPreview.setImages(images)
//        galleryPreview.thumbClickListener = {idx, view ->
//            navigator.openOver(GalleryFragment.newInstance(images, idx))
//        }

//        userRankingView.apply {
//            username = "Test User"
//            position = 34
//            completionTime = 3L * TimeConsts.SECONDS_IN_DAY + 5 * TimeConsts.SECONDS_IN_HOUR + 43
//            setAvatar(R.drawable.test_avatar)
//        }

//        firstPlaceEntryView.apply {
//            showData()
//            position = 1
//            username = "First User"
//            completionTime = 5L * TimeConsts.SECONDS_IN_HOUR + 20 * 60 + 43
//            setAvatar(R.drawable.test_avatar)
//        }
//
//        secondPlaceEntryView.apply {
//            showData()
//            position = 2
//            username = "Second User"
//            completionTime = 8L * TimeConsts.SECONDS_IN_HOUR + 45 * 60 + 43
//            setAvatar(R.drawable.test_avatar)
//        }
//
//        thirdPlaceEntryView.apply {
//            showData()
//            position = 3
//            username = "Third User"
//            completionTime = 10L * TimeConsts.SECONDS_IN_HOUR + 45 * 60 + 43
//            setAvatar(R.drawable.test_avatar)
//        }
//
//        fourthPlaceEntryView.apply {
//            showData()
//            position = 4
//            username = "Fourth User"
//            completionTime = 1L * TimeConsts.SECONDS_IN_DAY + 2 * TimeConsts.SECONDS_IN_HOUR + 43
//            setAvatar(R.drawable.test_avatar)
//        }
//
//        fifthPlaceEntryView.apply {
//            hideData()
//            position = 5
//        }

        ratingView.rateListener = {
            showRating()
        }
//        ratingStamp.alpha = 0f

        closeButton.setOnClickListener {
            navigator.goBack()
        }

        updateActionButton()
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe(this)
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
        timeLength.minLength = 10L * TimeConsts.SECONDS_IN_HOUR
        timeLength.maxLength = 3L * TimeConsts.SECONDS_IN_DAY + 5 * TimeConsts.SECONDS_IN_HOUR + 43
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
            galleryPreview.thumbClickListener = {idx, view ->
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