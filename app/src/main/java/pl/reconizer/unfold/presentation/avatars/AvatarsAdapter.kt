package pl.reconizer.unfold.presentation.avatars

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pl.reconizer.unfold.R
import pl.reconizer.unfold.domain.entities.Avatar

class AvatarsAdapter : RecyclerView.Adapter<AvatarViewHolder>() {

    var onSelectItemListener: ((selectedItem: Avatar) -> Unit)? = null

    var items = emptyList<Avatar>()

    val selectedAvatar: Avatar?
        get() {
            return if (selectedAvatarIdx < 0) {
                null
            } else {
                items[selectedAvatarIdx]
            }
        }

    private var selectedAvatarIdx: Int = -1

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvatarViewHolder {
        return AvatarViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_avatar_item, parent, false))
    }

    override fun onBindViewHolder(holder: AvatarViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty() && payloads[0] == SELECTION_CHANGED) {
            holder.isActive = position == selectedAvatarIdx
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun onBindViewHolder(holder: AvatarViewHolder, position: Int) {
        holder.isActive = position == selectedAvatarIdx
        holder.bind(items[position])

        holder.itemView.setOnClickListener {
            val previoslySelectedAvatarIdx = selectedAvatarIdx
            selectedAvatarIdx = position
            if (previoslySelectedAvatarIdx >= 0) {
                notifyItemChanged(previoslySelectedAvatarIdx, SELECTION_CHANGED)
            }
            notifyItemChanged(selectedAvatarIdx, SELECTION_CHANGED)
            onSelectItemListener?.invoke(items[selectedAvatarIdx])
        }
    }

    companion object {
        const val SELECTION_CHANGED = "selection_changed"
    }

}