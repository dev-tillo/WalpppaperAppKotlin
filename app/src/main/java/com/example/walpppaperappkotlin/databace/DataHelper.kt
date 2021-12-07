package com.example.walpppaperappkotlin.databace

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Entity::class], version = 1)
abstract class DataHelper : RoomDatabase() {

    abstract fun pdpDao(): WallpapperDao

    companion object {
        private var instance: DataHelper? = null

        @Synchronized
        fun getInstance(context: Context): DataHelper {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(context, DataHelper::class.java, "PdpAppRoomVersion")
                        .allowMainThreadQueries()
                        .build()
            }
            return instance!!
        }
    }
}