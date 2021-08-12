package com.example.albums.api

import com.example.albums.db.dao.AlbumDatabaseRepository
import com.example.albums.db.data.AlbumsData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AlbumsApiRepository(private val repository: AlbumDatabaseRepository) {

    private var albumsRemoteDataSource = AlbumsRemoteDataSource()

    suspend fun getAlbumList(): Flow<Result<List<AlbumsData>>?> {
        return flow {

            emit(Result.loadingStart())

            /** first loading offline data if available and
             * next keep calling api to fetch remote data,
             * on api success call list will be refreshed with latest data  */
            val data = repository.getAllAlbums()
            if (data.isNotEmpty()) {
                emit(Result.success(data))
            }

            val result = albumsRemoteDataSource.getAlbumList()

            //  Cache to database if response is successful
            if (result.status == Result.Status.SUCCESS) {
                result.data?.let {
                    if (it.isNotEmpty()) {
                        repository.deleteAllAlbums()
                        repository.insertAlbumsList(it)
                    }
                }
            }

            emit(result)

        }.flowOn(Dispatchers.IO)
    }
}