package com.example.beersearchapp.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.beersearchapp.R
import com.example.beersearchapp.presentation.viewmodel.BeerListMainViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_beer_list_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class BeerListMainActivity : AppCompatActivity() {
    private val beerListMainViewModel : BeerListMainViewModel by viewModel()
    private var isLastPage = false
    private var isLoadPage = false

    private val beerAdapter : BeerAdapter by lazy {
        BeerAdapter(this).apply {
            delegatesManager.addDelegate(BeerDelegateAdapter(this@BeerListMainActivity){
                //TODO 화면 이동
                Log.d("debug111",it.toString())
            })

        }
    }

    private val recyclerviewScrollListener : RecyclerviewScrollListener =  object :  RecyclerviewScrollListener() {
        override fun loadMoreItems(page: Int) {
            isLoadPage = true
            beerListMainViewModel.getBeerPagenation(page)
        }

        override fun isLastPage() = isLastPage

        override fun isLoading(): Boolean {
            return false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beer_list_main)

        beerListMainViewModel.getBeer()

        with(rv_beer) {
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
            adapter = beerAdapter
            addOnScrollListener(recyclerviewScrollListener)
        }

        beerListMainViewModel.beerEvent.observe(this, {
            it?.let {
                beerAdapter.addItems(it)
                beerAdapter.notifyDataSetChanged()
            }
        })

    }

}