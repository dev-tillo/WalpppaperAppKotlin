package com.example.walpppaperappkotlin.databace

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Entity(

    @PrimaryKey()
    var id: String,
    var name: String,
    var url: String,
    var counts: Int,
    var width: Int,
    var height: Int,

) : Serializable