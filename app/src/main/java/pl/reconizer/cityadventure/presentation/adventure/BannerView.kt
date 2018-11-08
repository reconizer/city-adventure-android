package pl.reconizer.cityadventure.presentation.adventure

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_adventure_banner.view.*
import pl.reconizer.cityadventure.R

class BannerView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var name: String
        get() = adventureName.text.toString()
        set(value) { adventureName.text = value }

    var rating: Float = 5.0f
        set(value) {
            if (field != value) {
                field = value
                updateRating()
            }
        }

    var ratingCounter: Int = 0
        set(value) {
            field = value
            ratingCount.text = "($value)"
        }

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.view_adventure_banner, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.BannerView)
            val image = typedArray.getResourceId(R.styleable.BannerView_android_src, 0)
            if (image > 0) {
                bannerImage.setImageResource(image)
            }
            name = typedArray.getString(R.styleable.BannerView_name) ?: ""
            typedArray.recycle()
        }

    }

    fun setImage(url: String) {
        Picasso.get()
                .load(url)
                .into(bannerImage)
    }

    private fun updateRating() {
        starsContainer.removeAllViews()
        val intRating = rating.toInt()
        for (i in 0 until intRating) {
            starsContainer.addView(buildStarView().apply {
                setImageResource(R.drawable.icon_rating_value_full)
            })
        }
        if (rating - intRating >= 0.75f) {
            starsContainer.addView(buildStarView().apply {
                setImageResource(R.drawable.icon_rating_value_full)
            })
        } else if (rating - intRating >= 0.25f) {
            starsContainer.addView(buildStarView().apply {
                setImageResource(R.drawable.icon_rating_value_half)
            })
        }
        ratingTextView.text = String.format("%.1f", rating)
    }

    private fun buildStarView(): ImageView {
        return ImageView(context).apply {
            scaleType = ImageView.ScaleType.FIT_XY
            adjustViewBounds = true
            layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT)
        }
    }

}