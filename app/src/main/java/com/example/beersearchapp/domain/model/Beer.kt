package com.example.beersearchapp.domain.model

import com.google.gson.annotations.SerializedName

data class Beer(
    val name: String,
    val tagline: String,
    val description: String,
    val imgUrl: String
)