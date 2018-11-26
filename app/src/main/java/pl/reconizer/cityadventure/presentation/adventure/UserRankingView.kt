package pl.reconizer.cityadventure.presentation.adventure

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.view_adventure_start_point_my_ranking.view.*
import pl.reconizer.cityadventure.R
import pl.reconizer.cityadventure.common.extensions.toPrettyTimeString

class UserRankingView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var username: String
        get() = usernameTextView.text.toString()
        set(value) { usernameTextView.text = value }

    var position: Int = 0
        set(value) {
            field = value
            positionTextView.text = value.toString()
        }

    var completionTime: Long = 0
        set(value) {
            field = value
            timeTextView.text = value.toPrettyTimeString()
        }

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.view_adventure_start_point_my_ranking, this, true)
    }

    fun setAvatar(@DrawableRes avatarResId: Int) {
        avatar.setImageResource(avatarResId)
    }
}