package com.example.dictionarycardswipe.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AcronymDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE )
    fun insertAcronymToDB(acronymEntity: AcronymEntity)

    @Query("SELECT * FROM acronyms order by `index` DESC")
    fun readAcronymsFromDB(): Flow<List<AcronymEntity>>

    @Query("DELETE FROM acronyms")
    fun nukeTable()
}