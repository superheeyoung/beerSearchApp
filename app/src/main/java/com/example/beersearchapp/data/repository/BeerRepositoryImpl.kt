package com.example.beersearchapp.data.repository

import com.example.beersearchapp.data.mapper.BeerEntityMapper
import com.example.beersearchapp.domain.model.Beer
import com.example.beersearchapp.domain.repository.BeerRepository
import io.reactivex.rxjava3.core.Single

class BeerRepositoryImpl(
    private val beerDataSource: BeerDataSource,
    private val beerEntityMapper: BeerEntityMapper
) : BeerRepository {
    override fun getBeerList(): Single<List<Beer>> {
        return beerDataSource.getBeerList().map(beerEntityMapper::transform)
    }
}