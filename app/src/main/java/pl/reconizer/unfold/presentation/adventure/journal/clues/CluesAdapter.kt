package pl.reconizer.unfold.presentation.adventure.journal.clues

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.reconizer.unfold.R
import pl.reconizer.unfold.domain.entities.Clue
import pl.reconizer.unfold.domain.entities.ClueType

class CluesAdapter : RecyclerView.Adapter<ClueViewHolder>() {

    var clues: List<Clue> = emptyList()

    var clueClickListener: ((clue: Clue) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClueViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (ClueType.fromInt(viewType)) {
            ClueType.TEXT -> TextClueViewHolder(inflater.inflate(R.layout.view_journal_clue_with_text, parent, false))
            ClueType.IMAGE -> ImageClueViewHolder(inflater.inflate(R.layout.view_journal_clue_with_image, parent, false))
            ClueType.AUDIO -> AudioClueViewHolder(inflater.inflate(R.layout.view_journal_clue_with_audio, parent, false))
            ClueType.VIDEO -> VideoClueViewHolder(inflater.inflate(R.layout.view_journal_clue_with_video, parent, false))
            ClueType.URL -> UrlClueViewHolder(inflater.inflate(R.layout.view_journal_clue_with_url, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return clues.size
    }

    override fun getItemViewType(position: Int): Int {
        return getClue(position).type.value
    }

    override fun onBindViewHolder(holder: ClueViewHolder, position: Int) {
        when(holder) {
            is TextClueViewHolder -> onBindTextClue(holder, position)
            is ImageClueViewHolder -> onBindImageClue(holder, position)
            is AudioClueViewHolder -> onBindAudioClue(holder, position)
            is VideoClueViewHolder -> onBindVideoClue(holder, position)
            is UrlClueViewHolder -> onBindUrlClue(holder, position)
        }
    }

    private fun onBindTextClue(holder: TextClueViewHolder, position: Int) {
        holder.bind(getClue(position), position)
    }

    private fun onBindImageClue(holder: ImageClueViewHolder, position: Int) {
        holder.bind(getClue(position), position)
        holder.image.setOnClickListener { clueClickListener?.invoke(getClue(position)) }
    }

    private fun onBindAudioClue(holder: AudioClueViewHolder, position: Int) {
        holder.bind(getClue(position), position)
        holder.startButton.setOnClickListener { clueClickListener?.invoke(getClue(position)) }
    }

    private fun onBindVideoClue(holder: VideoClueViewHolder, position: Int) {
        holder.bind(getClue(position), position)
        holder.thumbnail.setOnClickListener { clueClickListener?.invoke(getClue(position)) }
    }

    private fun onBindUrlClue(holder: UrlClueViewHolder, position: Int) {
        holder.bind(getClue(position), position)
        holder.url.setOnClickListener {
            clueClickListener?.invoke(getClue(position))
        }
    }

    private fun getClue(position: Int): Clue {
        return clues[position]
    }

}