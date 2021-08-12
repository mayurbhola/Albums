package com.example.albums.api

import com.example.albums.db.data.AlbumsData
import retrofit2.Response
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class AlbumsRemoteDataSource {

    suspend fun getAlbumList(): Result<List<AlbumsData>> {
        return getResponse(
            request = { ApiService.createApiServiceImplementation.getAlbumList() },
            defaultErrorMessage = "Sorry, Something went wrong! Please try again later. If you keep seeing this message please contact support!"
        )
    }

    private suspend fun <T> getResponse(
        request: suspend () -> Response<T>,
        defaultErrorMessage: String
    ): Result<T> {
        return try {
            Timber.e("Working in thread : ${Thread.currentThread().name}")
            val result = request.invoke()
            if (result.isSuccessful) {
                return Result.success(result.body())
            } else {
                val errorResponse = ErrorUtils.parseError(result, ApiService.retrofit)
                Timber.e("errorResponse - $errorResponse")
                Result.error(errorResponse?.message ?: defaultErrorMessage)
            }
        } catch (e: Throwable) {
            Timber.e("Throwable -- ${e.message}")
            when (e) {
                is SocketTimeoutException -> Result.error("Unable to connect! Please check your internet")
                is UnknownHostException -> Result.error("No internet connection")
                else -> Result.error("${e.message}")
            }
        }
    }
}