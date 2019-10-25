package pl.reconizer.unfold.presentation.adventure

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_adventure_start_point_my_ranking.view.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.common.extensions.toPrettyTimeStringFromSeconds
import pl.reconizer.unfold.presentation.customviews.ShadowGenerator

class UserRankingView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var shadowGenerator: ShadowGenerator? = null
        set(value) { avatarShadow.shadowGenerator = value }

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
            timeTextView.text = value.toPrettyTimeStringFromSeconds()
        }

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.view_adventure_start_point_my_ranking, this, true)

        Picasso.get()
                .load(R.drawable.start_point_user_ranking_background)
                .into(rankingBackground)

        Picasso.get()
                .load(R.drawable.start_point_user_ranking_frame)
                .into(frame)

        Picasso.get()
                .load(R.drawable.ranking_avatar_background)
                .into(avatarBackground)

        Picasso.get()
                .load(R.drawable.start_point_ranking_user_time_frame)
                .into(timeFrame)
    }

    fun setAvatar(url: String?) {
        Picasso.get()
                .load(url)
                .into(avatar)
    }
}