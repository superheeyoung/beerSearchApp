package com.example.beersearchapp.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.beersearchapp.R
import com.example.beersearchapp.databinding.ActivityBeerDetailBinding
import com.example.beersearchapp.presentation.util.*

class BeerDetailActivity: AppCompatActivity() {
    companion object {
        const val EXTRA_BEER_NAME = "extra_beer_name"
        const val EXTRA_BEER_TAGLINE = "extra_beer_tagline"
        const val EXTRA_DESCRIPTION = "extra_description"
        const val EXTRA_IMAGE_URL = "extra_image_url"
    }
    private lateinit var binding : ActivityBeerDetailBinding

    private val beerName by extraNotNull(EXTRA_BEER_NAME, "")
    private val beerTagLine by extraNotNull(EXTRA_BEER_TAGLINE, "")
    private val beerDescription by extraNotNull(EXTRA_DESCRIPTION, "")
    private val beerImageUrl by extraNotNull(EXTRA_IMAGE_URL, "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBeerDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        Glide.with(this).load(beerImageUrl).into(binding.imgBeerDetail)
        binding.tvBeerName.text = "맥주 이름 : $beerName"
        binding.tvBeerTagline.text = "맥주 tagline : $beerTagLine"
        binding.tvBeerDescription.text = "맥주 설명 : $beerDescription"
    }
}

