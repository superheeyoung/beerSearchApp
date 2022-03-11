package com.example.beersearchapp.domain.usecase

import com.example.beersearchapp.domain.model.Beer
import com.example.beersearchapp.domain.repository.BeerRepository
import io.reactivex.rxjava3.core.Single

class BeerUseCase(private val beerRepository: BeerRepository) {
    fun getBeerList(page: Int = 1,pageCount: Int = 20): Single<List<Beer>> {
        return beerRepository.getBeerList(page,pageCount)
    }
}