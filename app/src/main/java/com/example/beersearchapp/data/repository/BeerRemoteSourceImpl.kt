package com.example.beersearchapp.data.repository

import com.example.beersearchapp.data.api.BeerApi
import com.example.beersearchapp.data.entity.BeerEntity
import io.reactivex.rxjava3.core.Single

class BeerRemoteSourceImpl(private val beerApi: BeerApi) : BeerRemoteSource {
    override fun getBeerList(): Single<List<BeerEntity>> {
        return beerApi.getBeerList()
    }

}
