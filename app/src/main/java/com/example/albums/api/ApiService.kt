package com.example.albums.api

import com.example.albums.BuildConfig
import com.example.albums.db.data.AlbumsData
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import timber.log.Timber

interface ApiService {

    @GET("albums")
    suspend fun getAlbumList(): Response<List<AlbumsData>>

    companion object {

        private fun logger(): HttpLoggingInterceptor {
            val logger = HttpLoggingInterceptor { Timber.e(it) }
            logger.level = HttpLoggingInterceptor.Level.BODY
            return logger
        }

        private val okHttpClient: OkHttpClient by lazy {
            OkHttpClient.Builder()
                .addInterceptor(logger())
                .retryOnConnectionFailure(true)
                .build()
        }

        val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val createApiServiceImplementation: ApiService by lazy { retrofit.create(ApiService::class.java) }
    }
}