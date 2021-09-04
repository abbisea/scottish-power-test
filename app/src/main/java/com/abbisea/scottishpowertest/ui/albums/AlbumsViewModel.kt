package com.abbisea.scottishpowertest.ui.albums

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.abbisea.scottishpowertest.data.AlbumsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class AlbumsViewModel @Inject constructor(
    albumsUseCase: AlbumsUseCase
) : ViewModel() {

    val albums = albumsUseCase.getAlbums().cachedIn(viewModelScope)
}