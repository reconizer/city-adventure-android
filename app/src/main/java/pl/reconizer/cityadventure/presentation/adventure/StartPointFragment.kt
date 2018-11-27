package pl.reconizer.cityadventure.presentation.adventure

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
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.entities.DifficultyLevel
import pl.reconizer.cityadventure.presentation.common.BaseFragment
import pl.reconizer.cityadventure.presentation.gallery.GalleryFragment
import pl.reconizer.cityadventure.presentation.navigation.SharedTransitionElement

class StartPointFragment : BaseFragment() {

    val images = listOf(
            "https://dummyimage.com/1440x800/0033ff/fff",
            "https://dummyimage.com/600x800/ff001e/fff",
            "https://dummyimage.com/1200x800/000/fff",
            "https://dummyimage.com/960x800/0033ff/fff",
            "https://dummyimage.com/1920x1080/ff8400/fff",
            "https://dummyimage.com/1440x800/ff001e/fff",
            "https://dummyimage.com/1440x800/000/0033ff",
            "https://dummyimage.com/1440x800/000/ff001e",
            "https://dummyimage.com/1440x800/000/fff",
            "https://dummyimage.com/1440x800/000/fff"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = null
        reenterTransition = Fade()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_adventure_start_point, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        banner.setImage("https://placekitten.com/g/1000/1600")
        banner.name = "Some Cool Adventure"
        banner.rating = 4.6f
        banner.ratingCounter = 154

        authorInfo.name = "Cool Author"
        authorInfo.setLogo("https://placekitten.com/g/300/300")

        difficultyLevel.level = DifficultyLevel.MEDIUM

        timeLength.minLength = 10L * TimeConsts.SECONDS_IN_HOUR
        timeLength.maxLength = 3L * TimeConsts.SECONDS_IN_DAY + 5 * TimeConsts.SECONDS_IN_HOUR + 43

        adventureDescription.text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas leo magna, auctor et ornare a, auctor ac elit. Cras sit amet consectetur est. Suspendisse dui lacus, blandit non neque fermentum, tincidunt eleifend tortor. Phasellus faucibus volutpat sapien, at viverra turpis vestibulum eu. Donec iaculis ullamcorper dolor, a consectetur nulla. Donec eget semper neque. Donec interdum sagittis nisl. Etiam at metus posuere, tincidunt ligula a, tristique dui."

        galleryPreview.setImages(images)
        galleryPreview.thumbClickListener = {idx, view ->
            val frag = GalleryFragment.newInstance(images, idx)
//            val frag = GalleryItemFragment.newInstance(images[idx], "galleryItem_$idx}")
            navigator.openOver(
                    frag,
                    SharedTransitionElement(view, images[idx])
            )
        }

        userRankingView.apply {
            username = "Test User"
            position = 34
            completionTime = 3L * TimeConsts.SECONDS_IN_DAY + 5 * TimeConsts.SECONDS_IN_HOUR + 43
            setAvatar(R.drawable.test_avatar)
        }

        firstPlaceEntryView.apply {
            showData()
            position = 1
            username = "First User"
            completionTime = 5L * TimeConsts.SECONDS_IN_HOUR + 20 * 60 + 43
            setAvatar(R.drawable.test_avatar)
        }

        secondPlaceEntryView.apply {
            showData()
            position = 2
            username = "Second User"
            completionTime = 8L * TimeConsts.SECONDS_IN_HOUR + 45 * 60 + 43
            setAvatar(R.drawable.test_avatar)
        }

        thirdPlaceEntryView.apply {
            showData()
            position = 3
            username = "Third User"
            completionTime = 10L * TimeConsts.SECONDS_IN_HOUR + 45 * 60 + 43
            setAvatar(R.drawable.test_avatar)
        }

        fourthPlaceEntryView.apply {
            showData()
            position = 4
            username = "Fourth User"
            completionTime = 1L * TimeConsts.SECONDS_IN_DAY + 2 * TimeConsts.SECONDS_IN_HOUR + 43
            setAvatar(R.drawable.test_avatar)
        }

        fifthPlaceEntryView.apply {
            hideData()
            position = 5
        }

        ratingView.rateListener = {
            showRating()
        }
        ratingStamp.alpha = 0f

        closeButton.setOnClickListener {
            navigator.goBack()
        }
    }

    private fun showRating() {
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
                .setDuration(RATING_HIGHT_ANIMATION_DURATION)
                .setStartDelay(RATING_VISIBILITY_ANIMATION_DURATION + RATING_ANIMATION_DELAY)
                .withEndAction { ratingViewContainer.isGone = true }
                .start()
        showRatingStamp()
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
                .setStartDelay(RATING_VISIBILITY_ANIMATION_DURATION + RATING_HIGHT_ANIMATION_DURATION + RATING_ANIMATION_DELAY)
                .start()
    }

    companion object {
        const val ADVENTURE_PARAM = "adventure"
        const val RATING_ANIMATION_DELAY = 300L
        const val RATING_VISIBILITY_ANIMATION_DURATION = 200L
        const val RATING_HIGHT_ANIMATION_DURATION = 200L
        const val RATING_STAMP_ANIMATION_DURATION = 500L

        fun newInstance(adventure: Adventure): StartPointFragment {
            return StartPointFragment().apply {
                arguments = bundleOf(ADVENTURE_PARAM to adventure)
            }
        }

    }

}