package com.example.albums.db.dao

import com.example.albums.db.data.AlbumsData

class AlbumDatabaseRepository(private val albumDatabaseDao: AlbumDatabaseDao) {

    suspend fun insertAlbumsList(albumsDataList: List<AlbumsData>) {
        albumDatabaseDao.insertAlbumsList(albumsDataList)
    }

    suspend fun deleteAllAlbums() {
        albumDatabaseDao.deleteAllAlbums()
    }

    suspend fun getAllAlbums(): List<AlbumsData> = albumDatabaseDao.getAllAlbums()
}