package pl.reconizer.cityadventure.presentation.adventure

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import pl.reconizer.cityadventure.R

class RatingView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var rating: Int = 0
        private set

    var rateListener: ((rate: Int) -> Unit)? = null

    private val stars: List<ImageView>

    init {
        orientation = LinearLayout.HORIZONTAL
        layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        )
        var starViews = mutableListOf<ImageView>()
        for(i in 0 until 5) {
            val star = ImageView(context).apply {
                setImageResource(R.drawable.rating_star_off)
                adjustViewBounds = true
                scaleType = ImageView.ScaleType.FIT_CENTER
                layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        1f
                ).apply {
                    val margin = resources.getDimensionPixelOffset(R.dimen.space_sm)
                    setMargins(margin, 0, margin, 0)
                }
                setOnClickListener {
                    handleClick(i)
                }
            }
            addView(star)
            starViews.add(star)
        }
        stars = starViews
    }

    private fun handleClick(starIndex: Int) {
        rating = starIndex + 1
        stars.forEachIndexed { i, star ->
            if (i <= starIndex) {
                star.setImageResource(R.drawable.rating_star_on)
            } else {
                star.setImageResource(R.drawable.rating_star_off)
            }
        }
        rateListener?.invoke(rating)
    }
}