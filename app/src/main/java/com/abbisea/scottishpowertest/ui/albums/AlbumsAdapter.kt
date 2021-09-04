package com.abbisea.scottishpowertest.ui.albums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.abbisea.scottishpowertest.R
import com.abbisea.scottishpowertest.data.models.Album
import com.abbisea.scottishpowertest.databinding.ListItemAlbumBinding

class AlbumsAdapter : RecyclerView.Adapter<LaunchViewHolder>() {

    private var items: List<Album> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder =
        LaunchViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_album,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateData(newItems: List<Album>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size
}

class LaunchViewHolder(
    private val binding: ListItemAlbumBinding,
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(album: Album?) {
        binding.album = album
    }
}
