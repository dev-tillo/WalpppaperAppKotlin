package com.example.walpppaperappkotlin.databace

import androidx.room.*
import io.reactivex.Flowable

@Dao
interface WallpapperDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSplash(splashEntity: Entity)

    @Update
    fun editUser(entity: Entity)

    @Delete
    fun deleteSplash(splashEntity: Entity)

    @Query("select * from Entity where id=:n")
    fun isHave(n: String): Boolean

    @Query("select * from Entity")
    fun getAll(): Flowable<List<Entity>>

}