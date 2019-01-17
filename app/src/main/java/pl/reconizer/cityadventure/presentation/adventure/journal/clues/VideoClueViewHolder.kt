package pl.reconizer.cityadventure.presentation.adventure.journal.clues

import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_journal_clue_with_video.view.*
import pl.reconizer.cityadventure.domain.entities.Clue

class VideoClueViewHolder(view: View) : ClueViewHolder(view) {

    val thumbnail: ImageView = view.thumbnail

    override fun bind(clue: Clue, idx: Int) {
        super.bind(clue, idx)
        Picasso.get()
                .load(clue.originalResourceUrl)
                .into(thumbnail)
    }

}