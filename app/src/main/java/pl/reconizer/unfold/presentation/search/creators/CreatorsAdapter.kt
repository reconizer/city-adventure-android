package pl.reconizer.unfold.presentation.search.creators

import android.view.LayoutInflater
import android.view.ViewGroup
import pl.reconizer.unfold.R
import pl.reconizer.unfold.domain.entities.Creator
import pl.reconizer.unfold.presentation.common.recyclerview.PagedListAdapter

class CreatorsAdapter : PagedListAdapter<Creator, CreatorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreatorViewHolder {
        return CreatorViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_creator_item, parent, false))
    }

    override fun onBindViewHolder(holder: CreatorViewHolder, position: Int) {
        holder.bind(items[position])
    }

}