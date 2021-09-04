package com.abbisea.scottishpowertest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.abbisea.scottishpowertest.data.AlbumsUseCase
import com.abbisea.scottishpowertest.data.models.Album
import com.abbisea.scottishpowertest.ui.albums.AlbumsViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalPagingApi
class AlbumsViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // my method of testing livedata, an observer collects observed results into a list,
    // which can be inspected to compare with behaviour assumptions
    private val observerList = mutableListOf<Any>()

    private val mockData = mockk<PagingData<Album>>(relaxed = true)
    private val albumData = MutableLiveData<PagingData<Album>>()
    private val mockAlbumLiveData = mockk<MutableLiveData<PagingData<Album>>>(relaxed = true)
    private val mockAlbumsUseCase = mockk<AlbumsUseCase>(relaxed = true).apply {
        coEvery { getAlbums() } returns albumData
    }

    @Test
    fun `viewmodel provides album data from usecase`() {
        val viewModel = AlbumsViewModel(mockAlbumsUseCase)
        // due to nature of paging library, it is not possible to check for equality of
        // the mock paging data with what is collected, because the PagingData is transformed
        viewModel.albums.observeForever {
            observerList.add(it)
        }
        assertEquals(0, observerList.size)
        albumData.postValue(mockData)
        assertEquals(1, observerList.size)
        albumData.postValue(mockData)
        assertEquals(2, observerList.size)
    }

    @Test
    fun `viewmodel caches usecase livadata on init`() {
        mockkStatic("androidx.paging.PagingLiveData") //need to mock cachedIn to verify it
        val cachingTestUseCase = mockk<AlbumsUseCase>(relaxed = true).apply {
            coEvery { getAlbums() } returns mockAlbumLiveData
        }
        val viewModel = AlbumsViewModel(cachingTestUseCase)
        coVerify(exactly = 1) { mockAlbumLiveData.cachedIn(viewModel.viewModelScope) }
    }
}