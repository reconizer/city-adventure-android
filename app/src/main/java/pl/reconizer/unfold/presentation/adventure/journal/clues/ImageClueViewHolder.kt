package pl.reconizer.unfold.presentation.adventure.journal.clues

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isGone
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_journal_clue_with_image.view.*
import pl.reconizer.unfold.domain.entities.Clue

class ImageClueViewHolder(view: View) : ClueViewHolder(view) {

    val image: ImageView = view.content
    val content: TextView = view.contentText

    override fun bind(clue: Clue, idx: Int) {
        super.bind(clue, idx)
        content.text = clue.description
        content.isGone = clue.description.isNullOrEmpty()
        Picasso.get()
                .load(clue.originalResourceUrl)
                .into(image)
    }

}