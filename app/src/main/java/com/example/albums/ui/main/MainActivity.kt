package com.example.albums.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.albums.MyApplication
import com.example.albums.ViewModelFactory
import com.example.albums.api.Result
import com.example.albums.databinding.MainActivityBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels {
        ViewModelFactory((application as MyApplication).apiRepository)
    }

    private val viewBinding: MainActivityBinding by lazy {
        MainActivityBinding.inflate(layoutInflater)
    }

    private val albumsListAdapter by lazy { AlbumsListAdapter(ArrayList()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        init()
    }

    private fun init() {
        viewBinding.recyclerView.adapter = albumsListAdapter

        fetchRemoteAlbums()
    }

    private fun fetchRemoteAlbums() {
        mainViewModel.fetchAlbumsList().observe(this, { result ->
            when (result.status) {
                Result.Status.LOADING_START -> {
                    Timber.e("Status LOADING_START")
                    viewBinding.progressBar.visibility = View.VISIBLE
                }
                Result.Status.SUCCESS -> {
                    Timber.e("Status SUCCESS")
                    viewBinding.progressBar.visibility = View.GONE
                    result.data?.let { it ->
                        if (it.isNotEmpty()) {
                            albumsListAdapter.updateData(it)
                        }
                    }
                    viewBinding.txtNoAlbum.visibility = if (albumsListAdapter.itemCount > 0) View.GONE else View.VISIBLE
                }
                Result.Status.ERROR -> {
                    Timber.e("Status ERROR")
                    viewBinding.progressBar.visibility = View.GONE
                    result.message?.let {
                        Timber.e("Status ERROR - $it")
                        Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                        //Snackbar.make(viewBinding.root,it,Snackbar.LENGTH_LONG).show()
                    }
                    viewBinding.txtNoAlbum.visibility = if (albumsListAdapter.itemCount > 0) View.GONE else View.VISIBLE
                }
            }
        })
    }
}