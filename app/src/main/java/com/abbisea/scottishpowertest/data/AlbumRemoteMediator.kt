package com.abbisea.scottishpowertest.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.abbisea.scottishpowertest.data.api.PlaceholderService
import com.abbisea.scottishpowertest.data.database.AppDatabase
import com.abbisea.scottishpowertest.data.models.Album
import com.abbisea.scottishpowertest.data.models.RemoteKey
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class AlbumRemoteMediator(
    private val service: PlaceholderService,
    private val database: AppDatabase
) : RemoteMediator<Int, Album>() {

    companion object {
        const val PAGE_SIZE = 10
    }

    private val albumDao by lazy { database.albumDao() }
    private val remoteKeyDao by lazy { database.remoteKeyDao() }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Album>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        remoteKeyDao.getKey()
                    }
                    if (remoteKey.nextKey == null)
                        return MediatorResult.Success(endOfPaginationReached = true)
                    remoteKey.nextKey

                }
            }
            val response = service.getAlbums(page)
            val albums = response.map {
                Album(id = it.id, title = it.title)
            }

            val nextKey = if (response.size < PAGE_SIZE) null else page + 1

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.clearAll()
                    albumDao.clearAll()
                }
                remoteKeyDao.insertKey(RemoteKey(nextKey))
                albumDao.insertAll(albums)
            }

            return MediatorResult.Success(endOfPaginationReached = nextKey == null)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}