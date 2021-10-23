package com.example.beersearchapp.data.entity

import com.google.gson.annotations.SerializedName

data class BeerEntity(
    @SerializedName("id")
    val id : Int,
    @SerializedName("name")
    val name : String,
    @SerializedName("tagline")
    val tagline : String,
    @SerializedName("description")
    val description : String,
    @SerializedName("image_url")
    val imgUrl : String
)