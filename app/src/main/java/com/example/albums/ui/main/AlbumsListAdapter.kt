package com.example.albums.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.albums.databinding.RowAlbumBinding
import com.example.albums.db.data.AlbumsData

class AlbumsListAdapter(private val albumsArrayList: ArrayList<AlbumsData>) :
    RecyclerView.Adapter<AlbumsListAdapter.ViewHolder>() {

    class ViewHolder(
        private val viewBinding: RowAlbumBinding
    ) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(albumsData: AlbumsData) {
            viewBinding.txtTitle.text = ("${albumsData.userId}.${albumsData.id} ${albumsData.title}")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            RowAlbumBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(albumsArrayList[position])

    override fun getItemCount() = albumsArrayList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(arrayList: List<AlbumsData>) {
        albumsArrayList.clear()
        albumsArrayList.addAll(arrayList.sortedBy { it.title })
        notifyDataSetChanged()
    }
}
