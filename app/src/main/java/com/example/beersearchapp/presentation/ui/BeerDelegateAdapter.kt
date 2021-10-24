package com.example.beersearchapp.presentation.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beersearchapp.R
import com.example.beersearchapp.presentation.base.BaseViewHolder
import com.example.beersearchapp.presentation.model.BeerDisplayableItem
import com.example.beersearchapp.presentation.model.DisplayableItem
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import kotlinx.android.synthetic.main.item_beer_option.*

class BeerDelegateAdapter(private val context : Context, private val itemClick: (BeerDisplayableItem) -> Unit) : AdapterDelegate<List<DisplayableItem>>(){
    override fun isForViewType(items: List<DisplayableItem>, position: Int): Boolean {
        return items[position] is BeerDisplayableItem
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_beer_option, parent, false)
        )
    }

    override fun onBindViewHolder(
        items: List<DisplayableItem>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        val item = items[position] as BeerDisplayableItem
        with(holder as ViewHolder) {
            Glide.with(context).load(item.imgUrl).into(img_beer)
            tv_name.text = item.name
            tv_tag.text = item.tagline
            tv_order.text = item.id.toString()

            itemView.setOnClickListener {
                itemClick?.invoke(item)
            }
        }

    }

    class ViewHolder(dpItemView : View) : BaseViewHolder(dpItemView)
}