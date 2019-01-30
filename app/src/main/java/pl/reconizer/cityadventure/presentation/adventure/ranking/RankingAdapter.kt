package pl.reconizer.cityadventure.presentation.adventure.ranking

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.reconizer.cityadventure.domain.entities.RankingEntry

class RankingAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var ranking: List<RankingEntry> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(RankingEntryView(parent.context)) {}
    }

    override fun getItemCount(): Int {
        return ranking.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        ranking[position].let { entry ->
            (holder.itemView as RankingEntryView).apply {
                username = entry.nick
                this.position = entry.position
                completionTime = entry.completionTime
                setAvatar(entry.avatarUrl)
            }
        }
    }

}