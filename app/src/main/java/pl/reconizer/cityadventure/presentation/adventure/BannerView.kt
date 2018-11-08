package pl.reconizer.cityadventure.presentation.adventure

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_adventure_banner.view.*
import pl.reconizer.cityadventure.R

class BannerView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.view_adventure_banner, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.AuthorInfoView)
            val image = typedArray.getResourceId(R.styleable.BannerView_android_src, 0)
            if (image > 0) {
                bannerImage.setImageResource(image)
            }
            typedArray.recycle()
        }

    }

    fun setImage(url: String) {
        Picasso.get()
                .load(url)
                .into(bannerImage)
    }

}