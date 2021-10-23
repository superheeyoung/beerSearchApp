package com.example.beersearchapp.data.repository

import com.example.beersearchapp.data.entity.BeerEntity
import io.reactivex.rxjava3.core.Single

class BeerDataSourceimpl(private val beerRemoteSource: BeerRemoteSource) : BeerDataSource{
    override fun getBeerList(): Single<List<BeerEntity>> {
        return beerRemoteSource.getBeerList()
    }
}