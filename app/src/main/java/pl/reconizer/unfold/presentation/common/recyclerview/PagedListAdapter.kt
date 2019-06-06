package pl.reconizer.unfold.presentation.common.recyclerview

import androidx.recyclerview.widget.RecyclerView

abstract class PagedListAdapter<TEntity, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    protected var items = mutableListOf<TEntity>()

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItems(newItems: List<TEntity>) {
        val lastElementPosition = items.size
        items.addAll(newItems)
        notifyItemRangeInserted(lastElementPosition, newItems.size - 1)
    }

    fun clearItems() {
        items.clear()
        notifyDataSetChanged()
    }

}