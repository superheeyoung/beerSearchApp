package com.example.beersearchapp.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BeerDisplayableItem (
    val name: String,
    val tagline: String,
    val description: String,
    val imgUrl: String
) : DisplayableItem, Parcelable