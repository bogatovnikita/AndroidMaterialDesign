package com.hedgehog.androidmaterialdesign.domain.earth

import com.hedgehog.androidmaterialdesign.api.earth.EarthPictureResponse

interface EarthPictureRepository {
    suspend fun earthPicture(): EarthPictureResponse
}