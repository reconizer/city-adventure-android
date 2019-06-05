package pl.reconizer.unfold.presentation.userprofile.adventures

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_user_profile_adventure_item.view.*
import pl.reconizer.unfold.common.extensions.toPrettyTimeString
import pl.reconizer.unfold.domain.entities.UserAdventure

class AdventureViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val cover: ImageView = view.coverImage
    val adventureName: TextView = view.adventureName
    val position: TextView = view.rankingPosition
    val completionTime: TextView = view.completionTime

    fun bind(adventure: UserAdventure) {
        adventureName.text = adventure.name
        position.text = adventure.position.toString()
        completionTime.text = adventure.completionTime?.toPrettyTimeString()

        Picasso.get()
                .load(adventure.coverImage)
                .into(cover)
    }

}