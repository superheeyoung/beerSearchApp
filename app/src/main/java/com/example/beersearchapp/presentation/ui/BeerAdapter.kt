package com.example.beersearchapp.presentation.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.beersearchapp.presentation.model.BeerDisplayableItem
import com.example.beersearchapp.presentation.model.DisplayableItem
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager

class BeerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val delegatesManager = AdapterDelegatesManager<List<DisplayableItem>>()

    var beerItem = ArrayList<DisplayableItem>()
    var items = mutableListOf<BeerDisplayableItem>()

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
        //TODO viewType 같이 공부
        //layoutManager 3개
        delegatesManager.onCreateViewHolder(parent, viewType)


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //재사용 했을 때 계속 들어옴 if문으로 ui바꿀 때 else문 꼭 추가하기
        delegatesManager.onBindViewHolder(items, position, holder)
    }

    override fun getItemCount() = items.size


}