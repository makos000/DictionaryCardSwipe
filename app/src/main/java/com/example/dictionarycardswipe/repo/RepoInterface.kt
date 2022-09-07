package com.example.dictionarycardswipe.repo

import com.example.dictionarycardswipe.models.Model
import retrofit2.Response
import retrofit2.http.Query

interface RepoInterface {

    suspend fun getSF(@Query("search") search: String): Response<Model>

}