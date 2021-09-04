package com.abbisea.scottishpowertest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.ExperimentalPagingApi
import com.abbisea.scottishpowertest.data.AlbumsUseCase
import com.abbisea.scottishpowertest.data.database.AlbumDao
import com.abbisea.scottishpowertest.data.database.AppDatabase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class AlbumsUseCaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockAlbumDao = mockk<AlbumDao>(relaxed = true)
    private val mockDb = mockk<AppDatabase>(relaxed = true).apply {
        every { albumDao() } returns mockAlbumDao
    }

    @Test
    fun `albums usecase gets all albums from albumDao when livedata observed`() =
        runBlockingTest {
            val useCase = AlbumsUseCase(mockk(), mockDb)
            val liveData = useCase.getAlbums()
            verify(exactly = 0) { mockAlbumDao.getAllAlbums() }
            liveData.observeForever { }
            verify(exactly = 1) { mockAlbumDao.getAllAlbums() }
        }
}