package com.example.beersearchapp.presentation.model

data class BeerDisplayableItem (
    val id: Int,
    val name: String,
    val tagline: String,
    val description: String,
    val imgUrl: String
) : DisplayableItem