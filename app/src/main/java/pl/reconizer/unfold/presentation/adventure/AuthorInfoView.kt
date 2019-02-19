package pl.reconizer.unfold.presentation.adventure

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_adventure_author_info.view.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.presentation.customviews.ShadowGenerator

class AuthorInfoView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var shadowGenerator: ShadowGenerator? = null
        set(value) { authorImageShadow.shadowGenerator = value }

    var name: String
        get() = authorName.text.toString()
        set(value) {
            authorName.text = value
        }

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.view_adventure_author_info, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.AuthorInfoView)
            name = typedArray.getString(R.styleable.AuthorInfoView_android_text) ?: ""
            typedArray.recycle()
        }

        Picasso.get()
                .load(R.drawable.start_point_creator_background)
                .noFade()
                .into(authorBackground)

        Picasso.get()
                .load(R.drawable.start_point_creator_background_tape)
                .noFade()
                .into(authorBackgroundTape)

    }

    fun setLogo(imageUrl: String) {
        Picasso.get()
                .load(imageUrl)
                .into(authorImage)
    }
}