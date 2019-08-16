package pl.reconizer.unfold.presentation.userprofile.adventures

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import pl.reconizer.unfold.R
import pl.reconizer.unfold.domain.entities.UserAdventure
import pl.reconizer.unfold.presentation.common.recyclerview.PagedListAdapter

class UserAdventuresAdapter : PagedListAdapter<UserAdventure, AdventureViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdventureViewHolder {
        return AdventureViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_user_profile_adventure_item, parent, false))
    }

    override fun onBindViewHolder(holder: AdventureViewHolder, position: Int) {
        holder.bind(items[position])
    }

}