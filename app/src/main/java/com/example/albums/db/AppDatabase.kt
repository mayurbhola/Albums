package com.example.albums.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.albums.db.dao.AlbumDatabaseDao
import com.example.albums.db.data.AlbumsData

@Database(entities = [AlbumsData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun albumDatabaseDao(): AlbumDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context
        ): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "albums_database"
                ).fallbackToDestructiveMigration().build()

                INSTANCE = instance

                instance
            }
        }
    }
}