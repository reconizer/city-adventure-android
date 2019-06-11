package pl.reconizer.unfold.presentation.creatorprofile.adventures

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_creator_profile_adventure_item.view.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.common.extensions.getStringByNameBang
import pl.reconizer.unfold.common.extensions.prettyTimeStringRange
import pl.reconizer.unfold.domain.entities.CreatorAdventure

class AdventureViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val cover: ImageView = view.coverImage
    val completionTime: TextView = view.completionTime
    val difficultyLevel: TextView = view.difficultyLevel
    val rating: TextView = view.rating
    val price: TextView = view.price

    fun bind(adventure: CreatorAdventure) {
        price.setText(R.string.common_free)
        completionTime.text = prettyTimeStringRange(adventure.minFinishTime, adventure.maxFinishTime)
        rating.text = "%.2f".format(adventure.rating)
        difficultyLevel.text = itemView.resources.getStringByNameBang(
                itemView.context,
                "difficulty_level_${adventure.difficultyLevel.name.toLowerCase()}"
        )

        Picasso.get()
                .load(adventure.coverImage)
                .into(cover)
    }

}