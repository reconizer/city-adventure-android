package pl.reconizer.unfold.presentation.search.adventures

import android.view.LayoutInflater
import android.view.ViewGroup
import pl.reconizer.unfold.R
import pl.reconizer.unfold.domain.entities.Adventure
import pl.reconizer.unfold.presentation.common.recyclerview.PagedListAdapter

class AdventuresAdapter : PagedListAdapter<Adventure, AdventureViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdventureViewHolder {
        return AdventureViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_adventure_item_with_details, parent, false))
    }

    override fun onBindViewHolder(holder: AdventureViewHolder, position: Int) {
        holder.bind(items[position])
    }

}