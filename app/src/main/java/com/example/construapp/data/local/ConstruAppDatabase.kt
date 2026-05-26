package com.example.construapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ProductoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ConstruAppDatabase : RoomDatabase() {
    abstract fun productoDao(): ProductoDao

    companion object {
        const val DATABASE_NAME = "construapp.db"
    }
}
