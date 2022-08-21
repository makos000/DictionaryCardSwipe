package com.example.dictionarycardswipe.repo

import com.example.dictionarycardswipe.api.ApiDetails
import com.example.dictionarycardswipe.models.Model
import com.example.dictionarycardswipe.room.AcronymDao
import retrofit2.Response
import javax.inject.Inject

class RepoRemote @Inject constructor(val apiDetails: ApiDetails): RepoInterface{

    override suspend fun getSF(search: String): Response<Model> {
        return apiDetails.getSF(search)
    }
}