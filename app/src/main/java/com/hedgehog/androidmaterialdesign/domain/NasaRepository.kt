package com.hedgehog.androidmaterialdesign.domain

import com.hedgehog.androidmaterialdesign.api.PictureOfTheDayResponse

interface NasaRepository {
    suspend fun pictureOfTheDay(): PictureOfTheDayResponse
}