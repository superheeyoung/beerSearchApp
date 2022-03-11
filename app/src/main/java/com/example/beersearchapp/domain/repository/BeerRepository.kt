package com.example.beersearchapp.domain.repository

import com.example.beersearchapp.domain.model.Beer
import io.reactivex.rxjava3.core.Single

interface BeerRepository {
    fun getBeerList(page: Int, pageCount: Int): Single<List<Beer>>
}