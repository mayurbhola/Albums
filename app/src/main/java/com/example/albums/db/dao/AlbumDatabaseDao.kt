package com.example.albums.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.albums.db.data.AlbumsData

@Dao
interface AlbumDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlbumsList(albumsDataList: List<AlbumsData>)

    @Query("SELECT * FROM albums ORDER BY title")
    suspend fun getAllAlbums(): List<AlbumsData>

    @Query("DELETE FROM albums")
    suspend fun deleteAllAlbums()
}