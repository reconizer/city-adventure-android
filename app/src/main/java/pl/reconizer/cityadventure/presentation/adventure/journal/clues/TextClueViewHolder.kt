package pl.reconizer.cityadventure.presentation.adventure.journal.clues

import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.view_journal_clue_with_text.view.*
import pl.reconizer.cityadventure.domain.entities.Clue

class TextClueViewHolder(view: View) : ClueViewHolder(view) {

    val content: TextView = view.content

    override fun bind(clue: Clue, idx: Int) {
        super.bind(clue, idx)
        content.text = clue.description
    }
    
}