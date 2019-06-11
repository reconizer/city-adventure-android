package pl.reconizer.unfold.presentation.creatorprofile.adventures

import android.view.LayoutInflater
import android.view.ViewGroup
import pl.reconizer.unfold.R
import pl.reconizer.unfold.domain.entities.CreatorAdventure
import pl.reconizer.unfold.presentation.common.recyclerview.PagedListAdapter

class CreatorAdventuresAdapter : PagedListAdapter<CreatorAdventure, AdventureViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdventureViewHolder {
        return AdventureViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_creator_profile_adventure_item, parent, false))
    }

    override fun onBindViewHolder(holder: AdventureViewHolder, position: Int) {
        holder.bind(items[position])
    }

}