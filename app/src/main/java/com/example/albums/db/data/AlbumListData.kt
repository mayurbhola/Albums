package com.example.albums.db.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Keep
@Entity(tableName = "albums")
data class AlbumsData(
    @PrimaryKey
    val id: Int,
    val userId: Int,
    val title: String
) : Serializable