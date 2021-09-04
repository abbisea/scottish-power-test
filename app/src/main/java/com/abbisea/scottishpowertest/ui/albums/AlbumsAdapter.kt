package com.abbisea.scottishpowertest.ui.albums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.abbisea.scottishpowertest.R
import com.abbisea.scottishpowertest.data.models.Album
import com.abbisea.scottishpowertest.databinding.ListItemAlbumBinding

class AlbumsAdapter : PagingDataAdapter<Album, AlbumViewHolder>(AlbumDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder =
        AlbumViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_album,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class AlbumViewHolder(
    private val binding: ListItemAlbumBinding,
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(album: Album?) {
        binding.album = album
    }
}

private class AlbumDiffCallback : DiffUtil.ItemCallback<Album>() {

    override fun areItemsTheSame(oldItem: Album, newItem: Album) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Album, newItem: Album) =
        oldItem == newItem
}
