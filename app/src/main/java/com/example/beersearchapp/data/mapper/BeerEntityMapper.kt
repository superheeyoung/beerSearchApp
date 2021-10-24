package com.example.beersearchapp.data.mapper

import com.example.beersearchapp.data.entity.BeerEntity
import com.example.beersearchapp.domain.model.Beer

class BeerEntityMapper {
    fun transform(target : List<BeerEntity>) : List<Beer> = with(target) {
        return map { beer ->
            Beer(beer.id, beer.name, beer.tagline, beer.description, beer.imgUrl ?: "https://images.punkapi.com/v2/80.png")
        }
    }
}