package com.abbisea.scottishpowertest.ui.albums

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.abbisea.scottishpowertest.data.AlbumsUseCase
import com.abbisea.scottishpowertest.data.models.Album
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class AlbumsViewModel @Inject constructor(
    albumsUseCase: AlbumsUseCase
) : ViewModel() {
    val albums = MutableLiveData(listOf<Album>())

    init {
        viewModelScope.launch {
            val albumsData = albumsUseCase.getAlbums()
            albums.postValue(albumsData)
        }
    }
}