package com.example.beersearchapp.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.beersearchapp.R
import com.example.beersearchapp.presentation.util.afterTextChanged
import com.example.beersearchapp.presentation.util.launchActivity
import com.example.beersearchapp.presentation.viewmodel.BeerListMainViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_beer_list_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class BeerListMainActivity : AppCompatActivity() {
    private val compositDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    private val beerListMainViewModel: BeerListMainViewModel by viewModel()
    private val searchSubject: BehaviorSubject<String> = BehaviorSubject.create()

    private val beerAdapter: BeerAdapter by lazy {
        BeerAdapter(this).apply {
            delegatesManager.addDelegate(BeerDelegateAdapter(this@BeerListMainActivity) {
                with(this@BeerListMainActivity) {
                    launchActivity<BeerDetailActivity> {
                        putExtra(BeerDetailActivity.EXTRA_BEER_NAME, it.name)
                        putExtra(BeerDetailActivity.EXTRA_BEER_TAGLINE, it.tagline)
                        putExtra(BeerDetailActivity.EXTRA_DESCRIPTION, it.description)
                        putExtra(BeerDetailActivity.EXTRA_IMAGE_URL, it.imgUrl)
                    }
                }
            })
        }
    }

    private val recyclerviewScrollListener: RecyclerviewScrollListener =
        object : RecyclerviewScrollListener() {
            override fun loadMoreItems(page: Int) {
                /*
                * pagination 구현
                * */
                // beerListMainViewModel.getBeerPagenation(page)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beer_list_main)

        bt_search.setOnClickListener {
            beerListMainViewModel.search(et_search.text.toString().uppercase())
        }

        et_search.afterTextChanged {
            searchSubject.onNext(it)
        }

        beerListMainViewModel.getBeer()

        with(rv_beer) {
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
            adapter = beerAdapter
            addOnScrollListener(recyclerviewScrollListener)
        }

        beerListMainViewModel.beerEvent.observe(this, {
            it?.let {
                if (it.isNotEmpty()) {
                    tv_empty_search_result.isVisible = false
                    rv_beer.isVisible = true
                    beerAdapter.items = it.toMutableList()
                    beerAdapter.addItems(it)
                    beerAdapter.notifyDataSetChanged()
                } else {
                    tv_empty_search_result.isVisible = true
                    rv_beer.isVisible = false
                }
            }
        })


        compositDisposable += searchBeerSubject()
    }

    private fun searchBeerSubject() =
        searchSubject.debounce(300, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                beerListMainViewModel.search(it.uppercase())
            }


}