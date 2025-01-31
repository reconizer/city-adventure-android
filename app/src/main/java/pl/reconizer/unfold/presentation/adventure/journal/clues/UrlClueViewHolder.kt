package pl.reconizer.unfold.presentation.adventure.journal.clues

import android.text.SpannableString
import android.text.Spanned
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.TextView
import androidx.core.view.isGone
import kotlinx.android.synthetic.main.view_journal_clue_with_url.view.*
import pl.reconizer.unfold.domain.entities.Clue

class UrlClueViewHolder(view: View) : ClueViewHolder(view) {

    val url: TextView = view.contentUrl
    val content: TextView = view.contentText

    override fun bind(clue: Clue, idx: Int) {
        super.bind(clue, idx)
        url.text = SpannableString(clue.originalResourceUrl).apply {
            setSpan(UnderlineSpan(), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        content.isGone = clue.description.isNullOrEmpty()
        content.text = clue.description
    }
    
}