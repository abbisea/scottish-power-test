package com.abbisea.scottishpowertest

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.room.withTransaction
import com.abbisea.scottishpowertest.data.AlbumRemoteMediator
import com.abbisea.scottishpowertest.data.api.AlbumDTO
import com.abbisea.scottishpowertest.data.api.PlaceholderService
import com.abbisea.scottishpowertest.data.database.AlbumDao
import com.abbisea.scottishpowertest.data.database.AppDatabase
import com.abbisea.scottishpowertest.data.models.Album
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class AlbumRemoteMediatorTest {

    private companion object {
        const val TEST_TITLE = "TEST_TITLE"
        const val TEST_ID = 12
        const val TEST_USER_ID = 5
    }

    private val testAlbum = Album(TEST_ID, TEST_TITLE)

    private val dummyResponse = listOf(
        AlbumDTO(
            id = TEST_ID,
            userId = TEST_USER_ID,
            title = TEST_TITLE
        )
    )

    private val mockService = mockk<PlaceholderService>().apply {
        coEvery { getAlbums(any()) } returns dummyResponse
    }

    private val launchesSlot = slot<List<Album>>()
    private val mockAlbumDao = mockk<AlbumDao>(relaxed = true).apply {
        every { insertAll(capture(launchesSlot)) } returns Unit
    }
    private val mockDb = mockk<AppDatabase>(relaxed = true).apply {
        every { albumDao() } returns mockAlbumDao
    }

    @Before
    fun setup() {
        mockkStatic(
            "androidx.room.RoomDatabaseKt"
        )

        val transactionLambda = slot<suspend () -> Any>()
        coEvery { mockDb.withTransaction(capture(transactionLambda)) } coAnswers {
            transactionLambda.captured.invoke()
        }
    }

    @Test
    fun `AlbumRemoteMediator converts album dto from api to album model`() =
        runBlockingTest {
            val mediator = AlbumRemoteMediator(mockService, mockDb)
            mediator.load(LoadType.REFRESH, mockk(relaxed = true))
            val mediatorAlbumList = launchesSlot.captured
            assertEquals(1, mediatorAlbumList.size)
            val album = mediatorAlbumList.first()

            assertEquals(testAlbum, album)
        }
}
