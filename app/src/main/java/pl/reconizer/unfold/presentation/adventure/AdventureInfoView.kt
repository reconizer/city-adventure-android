package pl.reconizer.unfold.presentation.adventure

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.bartoszlipinski.viewpropertyobjectanimator.ViewPropertyObjectAnimator
import kotlinx.android.synthetic.main.view_adventure_info.view.*
import kotlinx.android.synthetic.main.view_adventure_start_point_rating.view.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.domain.entities.AdventureStartPoint
import pl.reconizer.unfold.presentation.customviews.ShadowGenerator

class AdventureInfoView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var adventureStartPoint: AdventureStartPoint? = null
        set(value) {
            field = value
            show()
        }

    var shadowGenerator: ShadowGenerator? = null
        set(value) {
            field = value
            authorInfo.shadowGenerator = shadowGenerator
            galleryPreview.shadowGenerator = shadowGenerator
        }

    var rateable: Boolean = true
    var isCompleted: Boolean = false

    var onAuthorClickListener: (() -> Unit)? = null

    var galleryImageClickListener: ((index: Int) -> Unit)? = null
    var rateListener: ((rateValue: Int) -> Unit)? = null

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.view_adventure_info, this, true)

        ratingView.rateListener = {
            showRating()
            rateListener?.invoke(it)
        }
        pivotY = 0f

        ratingViewContainer.isGone = true
        ratingStamp.isGone = true
        difficultyLevel.isGone = true
        timeLength.isGone = true
        galleryPreview.isGone = true

        authorInfo.setOnClickListener { onAuthorClickListener?.invoke() }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        pivotX = (right - left) / 2f
        super.onLayout(changed, left, top, right, bottom)
    }

    private fun show() {
        adventureStartPoint?.let {
            showBanner(it)
            showAuthor(it)
            showFinishTime(it)
            showGallery(it)
            showCurrentUserRating(it)

            difficultyLevel.isVisible = it.difficultyLevel != null
            it.difficultyLevel?.let { certainDifficultyLevel ->
                difficultyLevel.level = certainDifficultyLevel
            }

            adventureDescription.text = it.description
        }
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
        timeLength.minLength = adventureStartPoint.minFinishTime
        timeLength.maxLength = adventureStartPoint.maxFinishTime
        timeLength.isVisible = adventureStartPoint.minFinishTime != null
    }

    private fun showGallery(adventureStartPoint: AdventureStartPoint) {
        if (adventureStartPoint.gallery.isEmpty()) {
            galleryPreview.isGone = true
        } else {
            galleryPreview.isVisible = true
            galleryPreview.setImages(adventureStartPoint.gallery)
            galleryPreview.thumbClickListener = {idx, _ ->
                galleryImageClickListener?.invoke(idx)
            }
        }
    }

    private fun showCurrentUserRating(adventureStartPoint: AdventureStartPoint) {
        if (isCompleted && rateable) {
            ratingViewContainer.isGone = adventureStartPoint.currentUserRating != null
            ratingStamp.isGone = adventureStartPoint.currentUserRating == null
            ratingStamp.rating = adventureStartPoint.currentUserRating ?: 0
        } else {
            ratingViewContainer.isGone = true
            ratingStamp.isGone = true
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

    companion object {
        const val RATING_ANIMATION_DELAY = 300L
        const val RATING_VISIBILITY_ANIMATION_DURATION = 200L
        const val RATING_HEIGHT_ANIMATION_DURATION = 200L
        const val RATING_STAMP_ANIMATION_DURATION = 500L
    }
}