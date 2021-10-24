package com.example.beersearchapp.presentation.ui

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

abstract class RecyclerviewScrollListener : RecyclerView.OnScrollListener(){
    protected abstract fun loadMoreItems(page:Int)
    abstract fun isLastPage(): Boolean
    abstract fun isLoading(): Boolean
    private var currentPage = 1
    private var pastVisibleItems = 3
    private var visibleItemCount = 0
    private var totalItemCount = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (dy > 0) {
            recyclerView.run {
                layoutManager?.let {
                    if (allowLoadMore() && isNearToLastItem(it)) {
                        loadMoreItems(++currentPage)
                    }
                }
            }
        }
    }

    private fun allowLoadMore() = !isLoading() && !isLastPage()

    private fun isNearToLastItem(lm : RecyclerView.LayoutManager) : Boolean{
        when (lm) {
            is LinearLayoutManager -> {
                visibleItemCount = lm.childCount
                totalItemCount = lm.itemCount
                pastVisibleItems = lm.findFirstVisibleItemPosition()
            }
            is GridLayoutManager -> {
                visibleItemCount = lm.childCount
                totalItemCount = lm.itemCount
                pastVisibleItems = lm.findFirstVisibleItemPosition()
            }
            is StaggeredGridLayoutManager -> {
                visibleItemCount = lm.childCount
                totalItemCount = lm.itemCount
                pastVisibleItems = lm.findFirstVisibleItemPositions(IntArray(lm.spanCount))[0]
            }
            else -> throw IllegalStateException("Unsupported layout manager")
        }
        return visibleItemCount + pastVisibleItems >= totalItemCount
    }
}