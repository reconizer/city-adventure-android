package pl.reconizer.unfold.presentation.search.adventures

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_adventure_item_with_details.view.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.common.extensions.getStringByNameBang
import pl.reconizer.unfold.common.extensions.prettyTimeStringRange
import pl.reconizer.unfold.common.extensions.stringifyDifficultyLevel
import pl.reconizer.unfold.domain.entities.Adventure

class AdventureViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val title: TextView = view.adventureTitle
    val cover: ImageView = view.coverImage
    val completionTime: TextView = view.completionTime
    val difficultyLevel: TextView = view.difficultyLevel
    val rating: TextView = view.rating
    val price: TextView = view.price

    fun bind(adventure: Adventure) {
        title.text = adventure.name
        price.setText(R.string.common_free)
        completionTime.text = prettyTimeStringRange(adventure.minFinishTime, adventure.maxFinishTime)
        rating.text = "%.2f".format(adventure.rating)
        difficultyLevel.text = stringifyDifficultyLevel(
                itemView.context,
                adventure.difficultyLevel
        )

        Picasso.get()
                .load(adventure.coverImage)
                .into(cover)
    }

}