package pl.reconizer.cityadventure.presentation.adventure

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_adventure_author_info.view.*
import pl.reconizer.cityadventure.R

class AuthorInfoView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

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
            authorImage.setImageResource(typedArray.getResourceId(R.styleable.AuthorInfoView_android_src, R.drawable.test_banner))
            typedArray.recycle()
        }

    }

    fun setLogo(imageUrl: String) {
        Picasso.get()
                .load(imageUrl)
                .into(authorImage)
    }
}