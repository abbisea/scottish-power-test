package com.abbisea.scottishpowertest.ui.albums

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.abbisea.scottishpowertest.data.AlbumsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class AlbumsViewModel @Inject constructor(
    albumsUseCase: AlbumsUseCase
) : ViewModel() {
    // post albums to livedata
}