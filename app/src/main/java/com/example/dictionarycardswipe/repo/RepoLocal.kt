package com.example.dictionarycardswipe.repo

import com.example.dictionarycardswipe.room.AcronymDao
import com.example.dictionarycardswipe.room.AcronymEntity
import javax.inject.Inject

class RepoLocal @Inject constructor(val acronymDao: AcronymDao) {

    fun insertAcronymToDB(acronymEntity: AcronymEntity) {
        return acronymDao.insertAcronymToDB(acronymEntity)
    }

    fun readAcronymsFromDB() = acronymDao.readAcronymsFromDB()
}