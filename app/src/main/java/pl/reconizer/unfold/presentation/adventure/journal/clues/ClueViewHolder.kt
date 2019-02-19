package pl.reconizer.unfold.presentation.adventure.journal.clues

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.reconizer.unfold.R
import pl.reconizer.unfold.domain.entities.Clue

open class ClueViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val clueId: TextView = view.findViewById(R.id.clueIdTextView)

    open fun bind(clue: Clue, idx: Int) {
        clueId.text = (idx + 1).toString()
    }

}