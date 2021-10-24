package com.example.beersearchapp.presentation.ui

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.beersearchapp.presentation.model.BeerDisplayableItem
import com.example.beersearchapp.presentation.model.DisplayableItem
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class BeerAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val delegatesManager = AdapterDelegatesManager<List<DisplayableItem>>()

    private var beerItem = ArrayList<DisplayableItem>()

    fun addItems(items: List<DisplayableItem>) {
        removePagination()
        beerItem.addAll(items)
        if (items.isNotEmpty()) {
            notifyItemRangeInserted(beerItem.size, items.size)
        }
    }


    fun removePagination() {
        if (beerItem.isNotEmpty() && beerItem[beerItem.size - 1] is DisplayableItem) {
            beerItem.removeAt(beerItem.size - 1)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        delegatesManager.onCreateViewHolder(parent, viewType)


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegatesManager.onBindViewHolder(beerItem, position, holder)
    }

    override fun getItemCount() = beerItem.size


}