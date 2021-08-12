package com.example.albums

import android.app.Application
import com.example.albums.api.AlbumsApiRepository
import com.example.albums.db.AppDatabase
import com.example.albums.db.dao.AlbumDatabaseRepository
import timber.log.Timber

class MyApplication : Application() {

    private val database by lazy { AppDatabase.getDatabase(this) }
    private val repository by lazy { AlbumDatabaseRepository(database.albumDatabaseDao()) }
    val apiRepository by lazy { AlbumsApiRepository(repository) }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}