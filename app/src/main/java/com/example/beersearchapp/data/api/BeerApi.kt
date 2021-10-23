package com.example.beersearchapp.data.api

import com.example.beersearchapp.data.entity.BeerEntity
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface BeerApi {
    @GET("beers")
    fun getBeerList() : Single<List<BeerEntity>>
}