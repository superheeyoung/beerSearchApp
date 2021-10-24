package com.example.beersearchapp.domain.usecase

import com.example.beersearchapp.domain.model.Beer
import com.example.beersearchapp.domain.repository.BeerRepository
import io.reactivex.rxjava3.core.Single

class BeerUseCase(private val beerRepository: BeerRepository) {
    fun getBeerList() : Single<List<Beer>> {
        return beerRepository.getBeerList()
    }

    fun getBeerListPagination(pageCount : Int) : Single<List<Beer>> {
        return beerRepository.getBeerListPagination(pageCount)
    }
}