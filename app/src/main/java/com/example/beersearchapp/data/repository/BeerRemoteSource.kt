package com.example.beersearchapp.data.repository

import com.example.beersearchapp.data.entity.BeerEntity
import io.reactivex.rxjava3.core.Single

interface BeerRemoteSource
{
    fun getBeerList() : Single<List<BeerEntity>>
    fun getBeerListPagination(pageCount : Int) : Single<List<BeerEntity>>
}