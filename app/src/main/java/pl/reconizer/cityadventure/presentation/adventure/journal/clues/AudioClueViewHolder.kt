package pl.reconizer.cityadventure.presentation.adventure.journal.clues

import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.view_journal_clue_with_audio.view.*
import pl.reconizer.cityadventure.domain.entities.Clue

class AudioClueViewHolder(view: View) : ClueViewHolder(view) {

    val fileName: TextView = view.audioNameTextView

    override fun bind(clue: Clue, idx: Int) {
        super.bind(clue, idx)
        fileName.text = "audio.mp3"
    }
    
}