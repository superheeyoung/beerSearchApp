package com.example.beersearchapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beersearchapp.domain.usecase.BeerUseCase
import com.example.beersearchapp.presentation.mapper.BeerModelMapper
import com.example.beersearchapp.presentation.model.BeerModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import com.example.beersearchapp.presentation.util.Result
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class BeerListMainViewModel(
    private val beerUseCase: BeerUseCase,
    private val beerModelMapper: BeerModelMapper
) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val beerEvent = MutableLiveData<Result<List<BeerModel>>>()
    var beerList: ArrayList<BeerModel> = arrayListOf()

    fun getBeer() {
        compositeDisposable +=
            beerUseCase.getBeerList()
                .map(beerModelMapper::transform)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { beerEvent.value = Result.Loading }
                .subscribeBy(
                    onSuccess = { beer ->
                        beerEvent.value = Result.Success(beer)
                        this.beerList.addAll(beer)
                    }, onError = {
                        beerEvent.value = Result.Error(it)
                    }
                )


    }

    fun loadMore(pageCount: Int) {
        compositeDisposable +=
            beerUseCase.getBeerList(pageCount)
                .map(beerModelMapper::transform)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { beerEvent.value = Result.Paging }
                .subscribeBy(
                    onSuccess = { beer ->
                        this.beerList.addAll(beer)
                        beerEvent.value = Result.Success(beerList)
                    }, onError = {
                        beerEvent.value = Result.Error(it)
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
                    if (it.isEmpty()) beerEvent.value = Result.Success(arrayListOf())
                    else beerEvent.value = Result.Success(it)
                },
                onError = {
                    beerEvent.value = Result.Success(beerList)
                }
            )
    }
}