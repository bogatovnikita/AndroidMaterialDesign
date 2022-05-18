package com.hedgehog.androidmaterialdesign.domain.earth

import com.hedgehog.androidmaterialdesign.BuildConfig
import com.hedgehog.androidmaterialdesign.api.earth.EarthPictureApi
import com.hedgehog.androidmaterialdesign.api.earth.EarthPictureResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EarthPictureRepositoryImplementation : EarthPictureRepository {

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
        .create(EarthPictureApi::class.java)

    override suspend fun earthPicture(): EarthPictureResponse =
        api.earthPicture(BuildConfig.NASA_APP_KEY)
}