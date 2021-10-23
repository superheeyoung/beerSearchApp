package com.example.beersearchapp.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.beersearchapp.R
import com.example.beersearchapp.presentation.viewmodel.BeerListMainViewModel
import kotlinx.android.synthetic.main.activity_beer_list_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class BeerListMainActivity : AppCompatActivity() {

    private val beerListMainViewModel : BeerListMainViewModel by viewModel()
    private val beerAdapter : BeerAdapter by lazy {
        BeerAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beer_list_main)

        beerListMainViewModel.getBeer()

        with(rv_beer) {
            adapter = beerAdapter
        }

        beerListMainViewModel.beerEvent.observe(this, {
            it?.let {
                beerAdapter.items = it
                beerAdapter.notifyDataSetChanged()
            }
        })
    }
}