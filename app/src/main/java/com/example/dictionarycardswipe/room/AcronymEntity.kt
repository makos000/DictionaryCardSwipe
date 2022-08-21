package com.example.dictionarycardswipe.room


import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dictionarycardswipe.models.Model

@Entity(tableName = "acronyms")
class AcronymEntity(val acronymEntity: Model) {
    @PrimaryKey(autoGenerate = true)
    var index: Int = 0
}