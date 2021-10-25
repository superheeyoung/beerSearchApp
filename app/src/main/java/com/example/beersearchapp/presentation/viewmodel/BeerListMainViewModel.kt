package com.example.beersearchapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beersearchapp.domain.usecase.BeerUseCase
import com.example.beersearchapp.presentation.mapper.BeerModelMapper
import com.example.beersearchapp.presentation.model.BeerDisplayableItem
import com.example.beersearchapp.presentation.model.BeerModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class BeerListMainViewModel(
    private val beerUseCase: BeerUseCase,
    private val beerModelMapper: BeerModelMapper
) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val beerEvent = MutableLiveData<List<BeerDisplayableItem>>()
    var beerList : ArrayList<BeerDisplayableItem> = arrayListOf()

    fun getBeer() {
        compositeDisposable +=
            beerUseCase.getBeerList()
                .map(beerModelMapper::transform)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy { beer ->
                    beerEvent.value = beer
                    this.beerList.addAll(beer)
                }
    }

    fun getBeerPagenation(pageCount: Int) {
        compositeDisposable +=
            beerUseCase.getBeerListPagination(pageCount)
                .map(beerModelMapper::transform)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onSuccess = { beer ->
                        this.beerList.addAll(beer)
                        beerEvent.value = beerList
                    }, onError = {
                    }
                )
    }

    fun search(name: String) {
        compositeDisposable += Single.defer {
            Single.just(
                beerList.filter { it.name.uppercase().contains(name) }
            )
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    if(it.isEmpty()) beerEvent.value = arrayListOf()
                    else beerEvent.value = it
                },
                onError = {
                    beerEvent.value = beerList
                }
            )
    }
}