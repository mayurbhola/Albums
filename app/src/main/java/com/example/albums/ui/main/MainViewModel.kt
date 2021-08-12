package com.example.albums.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.albums.api.AlbumsApiRepository
import com.example.albums.api.Result
import com.example.albums.db.data.AlbumsData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(private val apiRepository: AlbumsApiRepository) : ViewModel() {
    private val albumsList = MutableLiveData<Result<List<AlbumsData>>>()

    fun fetchAlbumsList(): LiveData<Result<List<AlbumsData>>> {
        viewModelScope.launch {
            apiRepository.getAlbumList().collect {
                albumsList.value = it
            }
        }
        return albumsList
    }
}