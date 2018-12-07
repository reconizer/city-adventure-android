package pl.reconizer.cityadventure.presentation.adventure

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import com.bartoszlipinski.viewpropertyobjectanimator.ViewPropertyObjectAnimator
import kotlinx.android.synthetic.main.view_adventure_info.view.*
import kotlinx.android.synthetic.main.view_adventure_start_point_rating.view.*
import pl.reconizer.cityadventure.R
import pl.reconizer.cityadventure.domain.entities.AdventureStartPoint
import pl.reconizer.cityadventure.presentation.customviews.ShadowGenerator

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
            authorInfo.shadowGenerator = shadowGenerator
            galleryPreview.shadowGenerator = shadowGenerator
        }

    var rateable: Boolean = true
    var isCompleted: Boolean = false

    var galleryImageClickListener: ((index: Int) -> Unit)? = null
    var rateListener: ((rateValue: Int) -> Unit)? = null

    private var _scaleX: Float = 1f
    private var _scaleY: Float = 1f

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.view_adventure_info, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.AdventureInfoView)
            _scaleX = typedArray.getFloat(R.styleable.AdventureInfoView_android_scaleX, 1f)
            _scaleY = typedArray.getFloat(R.styleable.AdventureInfoView_android_scaleX, 1f)
            typedArray.recycle()
        }

        ratingView.rateListener = {
            showRating()
            rateListener?.invoke(it)
        }
//        container.scaleX = _scaleX
//        container.scaleY = _scaleY
        pivotY = 0f
    }

//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        Log.v("[View name] onMeasure w", MeasureSpec.toString(widthMeasureSpec))
//        Log.v("[View name] onMeasure h", MeasureSpec.toString(heightMeasureSpec))
//        val width = MeasureSpec.getSize(widthMeasureSpec)
//        val height = MeasureSpec.getSize(heightMeasureSpec)
//        if (container.measuredHeight > 0) {
//            setMeasuredDimension((width * scaleX).toInt(), (container.measuredHeight * scaleY).toInt())
//        } else {
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        }
//        if (height > 0) {
//            setMeasuredDimension((width * scaleX).toInt(), (height * scaleY).toInt())
//        } else {
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        }
//        if (container.measuredWidth > 0) {
//            //container.pivotX = container.measuredWidth / 2f
//            container.pivotY = 0f
//            setMeasuredDimension(width, (container.measuredHeight * scaleY).toInt())
//        } else {
//
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        }

//    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        pivotX = (right - left) / 2f
        super.onLayout(changed, left, top, right, bottom)
    }

//    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
//        //super.onLayout(changed, left, top, right, (bottom * _scaleY).toInt())
//        layout(left, top, right, (bottom * _scaleY).toInt())
//        //container.layout(left, top, right, (bottom * _scaleY).toInt())
//    }
//
//    override fun getScaleX(): Float {
//        return _scaleX
//    }
//
//    override fun getScaleY(): Float {
//        return _scaleY
//    }
//
//    override fun setScaleX(scaleX: Float) {
//        _scaleX = scaleX
////        container.scaleX = scaleX
////        requestLayout()
//    }
//
//    override fun setScaleY(scaleY: Float) {
//        _scaleY = scaleY
////        container.scaleY = scaleY
////        requestLayout()
//    }

    private fun show() {
        if (adventureStartPoint != null) {
            showBanner(adventureStartPoint!!)
            showAuthor(adventureStartPoint!!)
            showFinishTime(adventureStartPoint!!)
            showGallery(adventureStartPoint!!)
            showCurrentUserRating(adventureStartPoint!!)

            difficultyLevel.isGone = false
            difficultyLevel.level = adventureStartPoint!!.difficultyLevel

            adventureDescription.text = adventureStartPoint!!.description
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
        timeLength.isGone = false
    }

    private fun showGallery(adventureStartPoint: AdventureStartPoint) {
        if (adventureStartPoint.gallery.isEmpty()) {
            galleryPreview.isGone = true
        } else {
            galleryPreview.isGone = false
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