package pl.reconizer.unfold.presentation.search.creators

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_creator_item.view.*
import pl.reconizer.unfold.domain.entities.Creator

class CreatorViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val coverImage: ImageView = view.coverImage
    val name: TextView = view.creatorName
    val adventuresCount: TextView = view.adventuresCount
    val followersCount: TextView = view.followersCount

    fun bind(creator: Creator) {
        name.text = creator.name
        adventuresCount.text = creator.adventuresCount.toString()
        followersCount.text = creator.followersCount.toString()

        Picasso.get()
                .load(creator.logo)
                .into(coverImage)
    }
}