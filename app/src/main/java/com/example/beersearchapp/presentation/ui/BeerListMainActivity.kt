package com.example.beersearchapp.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
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
    private lateinit var binding : ActivityBeerListMainBinding
    private val compositDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    private val beerAdapter : BeerAdapter by lazy {
        BeerAdapter {
            launchActivity<BeerDetailActivity> (
                BeerDetailActivity.EXTRA_BEER_NAME to it.name,
                BeerDetailActivity.EXTRA_BEER_TAGLINE to it.tagline,
                BeerDetailActivity.EXTRA_DESCRIPTION to it.description,
                BeerDetailActivity.EXTRA_IMAGE_URL to it.imgUrl,
            )
        }
    }

    private val beerListMainViewModel: BeerListMainViewModel by viewModel()
    private val searchSubject: BehaviorSubject<String> = BehaviorSubject.create()

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
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@BeerListMainActivity)
            adapter = beerAdapter

        }

        beerListMainViewModel.beerEvent.observe(this, {
            it?.let {
                if (it.isNotEmpty()) {
                    binding.tvEmptySearchResult.isVisible = false
                    binding.rvBeer.isVisible = true
                    beerAdapter.beerList = it
                    beerAdapter.notifyDataSetChanged()

                } else {
                    binding.tvEmptySearchResult.isVisible = true
                    binding.rvBeer.isVisible = false
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