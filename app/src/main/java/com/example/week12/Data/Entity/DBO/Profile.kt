package com.example.week12.Data.Entity.DBO

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Users")
data class Profile(
    @ColumnInfo(name = "image")
    val img: String = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRS0IQhVr9DDJCq61QX28zCoiqDrvezBh5ylw&s",
    @PrimaryKey
    @ColumnInfo(name = "nickname")
    val nickName: String = "Ryqite",
    @ColumnInfo(name = "fullName")
    val fullName: String = "Гарбарук Даниил Александрович",
    @ColumnInfo(name = "password")
    val password: String = ""
)
