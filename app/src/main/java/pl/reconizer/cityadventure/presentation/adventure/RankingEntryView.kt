package pl.reconizer.cityadventure.presentation.adventure

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.view_ranking_entry.view.*
import pl.reconizer.cityadventure.R
import pl.reconizer.cityadventure.common.extensions.toPrettyTimeString

class RankingEntryView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var username: String
        get() = usernameTextView.text.toString()
        set(value) { usernameTextView.text = value }

    var position: Int = 0
        set(value) {
            field = value
            placementTextView.text = value.toString()
            placementDecoration.isVisible = true
            when (value) {
                1 -> placementDecoration.setImageResource(R.drawable.ranking_first_place)
                2 -> placementDecoration.setImageResource(R.drawable.ranking_second_place)
                3 -> placementDecoration.setImageResource(R.drawable.ranking_third_place)
                else -> placementDecoration.isVisible = false
            }
        }

    var completionTime: Long = 0
        set(value) {
            field = value
            timeTextView.text = value.toPrettyTimeString()
        }

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.view_ranking_entry, this, true)
    }

    fun setAvatar(@DrawableRes avatarResId: Int) {
        avatarImageView.setImageResource(avatarResId)
    }

    fun showData() {
        avatarBackground.isVisible = true
        avatarImageView.isVisible = true
        usernameTextView.isVisible = true
        timeTextView.isVisible = true
    }

    fun hideData() {
        avatarBackground.isVisible = false
        avatarImageView.isVisible = false
        usernameTextView.isVisible = false
        timeTextView.isVisible = false
    }
}