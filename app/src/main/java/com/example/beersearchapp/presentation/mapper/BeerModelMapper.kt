package com.example.beersearchapp.presentation.mapper

import com.example.beersearchapp.domain.model.Beer
import com.example.beersearchapp.presentation.model.BeerDisplayableItem
import com.example.beersearchapp.presentation.model.BeerModel

class BeerModelMapper {
    fun transform(target : List<Beer>) : List<BeerDisplayableItem> = with(target) {
        return map { beer ->
            BeerDisplayableItem(beer.name, beer.tagline, beer.description, beer.imgUrl)
        }
    }
}