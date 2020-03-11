package com.nextus.baseapp.ui.main

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nextus.baseapp.model.GistItem

@BindingAdapter("app:items")
fun setItems(recyclerView: RecyclerView, items: List<GistItem>) {
    if(recyclerView.adapter != null)
        (recyclerView.adapter as GistListAdapter).submitList(items)
}