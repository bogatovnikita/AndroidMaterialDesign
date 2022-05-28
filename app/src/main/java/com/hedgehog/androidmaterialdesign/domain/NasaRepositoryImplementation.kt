package com.hedgehog.androidmaterialdesign.domain

import com.hedgehog.androidmaterialdesign.BuildConfig
import com.hedgehog.androidmaterialdesign.api.NasaApi
import com.hedgehog.androidmaterialdesign.api.PictureOfTheDayResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NasaRepositoryImplementation : NasaRepository {

    private val api = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.nasa.gov/")
        .client(OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
            .build()
        )
        .build()
        .create(NasaApi::class.java)

    override suspend fun pictureOfTheDay(): PictureOfTheDayResponse =
        api.pictureOfTheDay(BuildConfig.NASA_API_KEY)
}