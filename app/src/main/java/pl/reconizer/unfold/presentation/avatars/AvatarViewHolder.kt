package pl.reconizer.unfold.presentation.avatars

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_avatar_item.view.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.domain.entities.Avatar

class AvatarViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val background: ImageView = view.avatarBackground
    val avatar: ImageView = view.avatar

    var isActive: Boolean = false
        set(value) {
            field = value
            if (value) {
                background.imageTintList = ContextCompat.getColorStateList(itemView.context, R.color.colorAccent)
            } else {
                background.imageTintList = null
            }
        }

    fun bind(avatarData: Avatar) {
        Picasso.get()
                .load(avatarData.url)
                .into(avatar)
    }

}