package com.example.sewakendaraan.room.kendaraanRoom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Kendaraan::class],
    version = 1
)

abstract class KendaraanDB: RoomDatabase() {
    abstract fun kendaraanDao() : KendaraanDao
    companion object {

        @Volatile private var instance : KendaraanDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?:
        synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                KendaraanDB::class.java,
                "kendaraan12345.db"
            ).build()

    }

}