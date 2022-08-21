package com.example.dictionarycardswipe.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities = [AcronymEntity::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(RoomTypeConverter::class)
abstract class AcronymDatabase: RoomDatabase() {
    abstract fun acronymDao(): AcronymDao
}