package com.example.beersearchapp.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.example.beersearchapp.presentation.util.Result
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beersearchapp.databinding.ActivityBeerListMainBinding
import com.example.beersearchapp.presentation.util.launchActivity
import com.example.beersearchapp.presentation.viewmodel.BeerListMainViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.subjects.BehaviorSubject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class BeerListMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBeerListMainBinding
    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    private val beerAdapter: BeerAdapter by lazy {
        BeerAdapter {
            launchActivity<BeerDetailActivity>(
                BeerDetailActivity.EXTRA_BEER_NAME to it.name,
                BeerDetailActivity.EXTRA_BEER_TAGLINE to it.tagline,
                BeerDetailActivity.EXTRA_DESCRIPTION to it.description,
                BeerDetailActivity.EXTRA_IMAGE_URL to it.imgUrl,
            )
        }
    }

    private val beerListMainViewModel: BeerListMainViewModel by viewModel()
    private val searchSubject: BehaviorSubject<String> = BehaviorSubject.create()

    private var isLoading = false
    private var isLastPage = false

    private val paginationScrollListener: PaginationScrollListener =
        object : PaginationScrollListener() {
            override fun loadMoreItems(page: Int) {
                beerListMainViewModel.loadMore(page)
            }

            override fun getTotalPageCount() = 20

            override fun isLastPage() = isLastPage

            override fun isLoading() = isLoading
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBeerListMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btSearch.setOnClickListener {
            beerListMainViewModel.search(binding.etSearch.text.toString().uppercase())
        }

        binding.etSearch.doAfterTextChanged {
            searchSubject.onNext(binding.etSearch.text.toString())
        }

        beerListMainViewModel.getBeer()

        with(binding.rvBeer) {
            setHasFixedSize(true)
            addOnScrollListener(paginationScrollListener)
            layoutManager = LinearLayoutManager(this@BeerListMainActivity)
            adapter = beerAdapter
        }

        beerListMainViewModel.beerEvent.observe(this, {
            when (it) {
                is Result.Loading -> {
                    binding.prBar.isVisible = true
                }
                is Result.Paging -> {
                    isLoading = true
                    binding.prBar.isVisible = true
                }
                is Result.Success -> {
                    binding.prBar.isVisible = false

                    isLastPage = it.data.size < 20
                    isLoading = false

                    if (it.data.isNotEmpty()) {
                        binding.tvEmptySearchResult.isVisible = false
                        binding.rvBeer.isVisible = true
                        beerAdapter.beerList = it.data
                        beerAdapter.notifyDataSetChanged()
                    } else {
                        binding.tvEmptySearchResult.isVisible = true
                        binding.rvBeer.isVisible = false
                    }
                }
                is Result.Error -> {
                    isLoading = false
                }
            }
        })

        compositeDisposable += searchBeerSubject()
    }

    private fun searchBeerSubject() =
        searchSubject.debounce(300, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                beerListMainViewModel.search(it.uppercase())
            }
}