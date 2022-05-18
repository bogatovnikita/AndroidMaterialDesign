package com.hedgehog.androidmaterialdesign.api.earth

import retrofit2.http.GET
import retrofit2.http.Query

interface EarthPictureApi {
    @GET("EPIC/api/natural/images")
    suspend fun earthPicture(@Query("api_key") key: String): EarthPictureResponse
}