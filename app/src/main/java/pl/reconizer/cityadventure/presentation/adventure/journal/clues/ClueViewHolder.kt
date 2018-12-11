package pl.reconizer.cityadventure.presentation.adventure.journal.clues

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.reconizer.cityadventure.R

class ClueViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val clueId: TextView = view.findViewById(R.id.clueIdTextView)

    open fun bind() {

    }
}