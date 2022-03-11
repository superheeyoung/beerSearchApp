package com.example.beersearchapp.data.api

import com.example.beersearchapp.data.entity.BeerEntity
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface BeerApi {
    @GET("beers")
    fun getBeerList(
        @Query("page") pageCount: Int = 1,
        @Query("per_page") perPage: Int = 20
    ): Single<List<BeerEntity>>
}