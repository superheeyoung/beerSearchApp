package com.example.beersearchapp.data.repository

import com.example.beersearchapp.data.entity.BeerEntity
import io.reactivex.rxjava3.core.Single


class BeerDataSourceImpl(private val beerRemoteSource: BeerRemoteSource) : BeerDataSource {
    override fun getBeerList(page: Int, pageCount: Int): Single<List<BeerEntity>> {
        return beerRemoteSource.getBeerList(page, pageCount)
    }
}