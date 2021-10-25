package com.example.beersearchapp.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.beersearchapp.R
import com.example.beersearchapp.presentation.util.*
import kotlinx.android.synthetic.main.activity_beer_detail.*

class BeerDetailActivity: AppCompatActivity() {
    companion object {
        val EXTRA_BEER_NAME = "extra_beer_name"
        val EXTRA_BEER_TAGLINE = "extra_beer_tagline"
        val EXTRA_DESCRIPTION = "extra_description"
        val EXTRA_IMAGE_URL = "extra_image_url"
    }

    private val beerName by extraNotNull(EXTRA_BEER_NAME, "")
    private val beerTagLine by extraNotNull(EXTRA_BEER_TAGLINE, "")
    private val beerDescription by extraNotNull(EXTRA_DESCRIPTION, "")
    private val beerImageUrl by extraNotNull(EXTRA_IMAGE_URL, "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beer_detail)


        Glide.with(this).load(beerImageUrl).into(img_beer_detail)
        tv_beer_name.text = "맥주 이름 : $beerName"
        tv_beer_tagline.text = "맥주 tagline : $beerTagLine"
        tv_beer_description.text = "맥주 설명 : $beerDescription"
    }
}

