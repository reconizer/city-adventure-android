package pl.reconizer.cityadventure.presentation.adventure.journal.clues

import android.view.View
import android.widget.TextView
import androidx.core.view.isGone
import kotlinx.android.synthetic.main.view_journal_clue_with_url.view.*
import pl.reconizer.cityadventure.domain.entities.Clue

class UrlClueViewHolder(view: View) : ClueViewHolder(view) {

    val url: TextView = view.contentUrl
    val content: TextView = view.contentText

    override fun bind(clue: Clue, idx: Int) {
        super.bind(clue, idx)
        content.text = clue.description
        content.isGone = clue.description.isNullOrEmpty()
        url.text = clue.originalResourceUrl
    }
    
}