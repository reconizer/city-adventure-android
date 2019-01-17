package pl.reconizer.cityadventure.presentation.adventure.journal.clues

import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_journal_clue_with_image.view.*
import pl.reconizer.cityadventure.domain.entities.Clue

class ImageClueViewHolder(view: View) : ClueViewHolder(view) {

    val image: ImageView = view.content

    override fun bind(clue: Clue, idx: Int) {
        super.bind(clue, idx)
        Picasso.get()
                .load(clue.originalResourceUrl)
                .into(image)
    }

}