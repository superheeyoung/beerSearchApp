package com.example.beersearchapp.presentation.ui

import android.content.Context
import com.example.beersearchapp.presentation.model.DisplayableItem
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class BeerAdapter(private val context : Context) : ListDelegationAdapter<List<DisplayableItem>>() {

    private val beerDelegationAdapter = BeerDelegateAdapter(context)

    init {
        delegatesManager.addDelegate(beerDelegationAdapter)
    }

   /* fun onItemClickListener(listener : )
    */

}