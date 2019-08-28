package pl.reconizer.unfold.presentation.useradventures

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_user_adventure_item.view.*
import pl.reconizer.unfold.domain.entities.UserAdventure

class AdventureViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val cover: ImageView = view.coverImage
    val adventureName: TextView = view.adventureName

    fun bind(adventure: UserAdventure) {
        adventureName.text = adventure.name

        Picasso.get()
                .load(adventure.coverImage)
                .into(cover)
    }

}