package com.abbisea.scottishpowertest.data

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.abbisea.scottishpowertest.data.api.PlaceholderService
import com.abbisea.scottishpowertest.data.database.AppDatabase
import com.abbisea.scottishpowertest.data.models.Album
import javax.inject.Inject

class AlbumsUseCase @Inject constructor(
    private val placeholderService: PlaceholderService,
    private val appDatabase: AppDatabase
) {

    private val albumDao = appDatabase.albumDao()

    @ExperimentalPagingApi
    fun getAlbums(): LiveData<PagingData<Album>> = Pager(
        config = PagingConfig(enablePlaceholders = false, pageSize = AlbumRemoteMediator.PAGE_SIZE),
        remoteMediator = AlbumRemoteMediator(
            placeholderService,
            appDatabase
        )
    ) {
        albumDao.getAllAlbums()
    }.liveData
}