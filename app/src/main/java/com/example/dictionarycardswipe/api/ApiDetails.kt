package com.example.dictionarycardswipe.api

import com.example.dictionarycardswipe.models.Model
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiDetails {

    @GET(ApiReference.SF_ENDPOINT)
    suspend fun getSF(@Query("sf") search: String): Response<Model>
}