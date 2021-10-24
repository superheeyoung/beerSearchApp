package com.example.beersearchapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.beersearchapp.domain.usecase.BeerUseCase
import com.example.beersearchapp.presentation.mapper.BeerModelMapper
import com.example.beersearchapp.presentation.model.BeerDisplayableItem
import com.example.beersearchapp.presentation.model.BeerModel
import com.example.beersearchapp.presentation.util.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class BeerListMainViewModel(
    private val beerUseCase: BeerUseCase,
    private val beerModelMapper: BeerModelMapper
) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val beerEvent = SingleLiveEvent<List<BeerDisplayableItem>>()

    fun getBeer() {
        compositeDisposable +=
            beerUseCase.getBeerList()
                .map(beerModelMapper::transform)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy { beerList ->
                    beerEvent.value = beerList
                }
    }

    fun getBeerPagenation(pageCount: Int) {
        compositeDisposable +=
            beerUseCase.getBeerListPagination(pageCount)
                .map(beerModelMapper::transform)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { beerList ->
                        beerEvent.value = beerList
                        Log.d("debug222", beerList.toString())
                    }, onError = {
                        Log.d("debug222-e", it.toString())
                    }
                )
    }
}