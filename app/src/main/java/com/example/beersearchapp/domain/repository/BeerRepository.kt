package com.example.beersearchapp.domain.repository

import com.example.beersearchapp.domain.model.Beer
import io.reactivex.rxjava3.core.Single

interface BeerRepository {
    fun getBeerList() : Single<List<Beer>>
    fun getBeerListPagination(pageCount : Int) : Single<List<Beer>>
}