package com.nextus.baseapp.ui.main

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nextus.baseapp.databinding.ItemGistBinding
import com.nextus.baseapp.model.GistItem

class GistListAdapter(
    private val viewModel: MainViewModel
): ListAdapter<GistItem, GistListAdapter.ViewHolder>(GistDiffCallback()) {

    var tracker: SelectionTracker<Long>? = null

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        tracker?.let {
            holder.bind(viewModel, item, it.isSelected(position.toLong()))
        }
    }

    override fun getItemId(position: Int): Long = position.toLong()

    class ViewHolder private constructor(private val binding: ItemGistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(mainViewModel: MainViewModel, item: GistItem, isSelected: Boolean) {
            binding.viewModel = mainViewModel
            binding.item = item
            binding.urlText.setBackgroundColor(
                if(isSelected) {
                    itemView.isActivated = true
                    Color.parseColor("#ff4081")
                }
                else {
                    itemView.isActivated = false
                    Color.parseColor("#dedede")
                }
            )
            //binding.executePendingBindings()
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Long? = itemId
            }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemGistBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

}


/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class GistDiffCallback : DiffUtil.ItemCallback<GistItem>() {
    override fun areItemsTheSame(oldItem: GistItem, newItem: GistItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GistItem, newItem: GistItem): Boolean {
        return oldItem == newItem
    }
}