package pl.reconizer.unfold.presentation.avatars

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.reconizer.unfold.R
import pl.reconizer.unfold.domain.entities.Avatar

class AvatarsAdapter : RecyclerView.Adapter<AvatarViewHolder>() {

    var items = emptyList<Avatar>()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvatarViewHolder {
        return AvatarViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_avatar_item, parent, false))
    }

    override fun onBindViewHolder(holder: AvatarViewHolder, position: Int) {
        holder.bind(items[position])
    }

}