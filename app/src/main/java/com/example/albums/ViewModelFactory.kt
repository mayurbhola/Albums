package com.example.albums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.albums.api.AlbumsApiRepository
import com.example.albums.ui.main.MainViewModel

class ViewModelFactory(private val apiRepository: AlbumsApiRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(apiRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}