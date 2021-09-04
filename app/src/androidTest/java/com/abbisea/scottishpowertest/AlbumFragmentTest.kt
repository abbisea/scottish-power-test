package com.abbisea.scottishpowertest

import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.abbisea.scottishpowertest.data.api.PlaceholderService
import com.abbisea.scottishpowertest.data.database.AppDatabase
import com.abbisea.scottishpowertest.data.models.Album
import com.abbisea.scottishpowertest.hiltmodules.DatabaseModule
import com.abbisea.scottishpowertest.hiltmodules.NetworkModule
import com.abbisea.scottishpowertest.utils.atPosition
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.mockk.coEvery
import io.mockk.mockk
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.BufferedSource
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException

@ExperimentalPagingApi
@UninstallModules(DatabaseModule::class, NetworkModule::class)
@HiltAndroidTest
class AlbumFragmentTest {

    private companion object {
        const val TEST_TITLE = "TEST_TITLE"
        const val TEST_ID = 12
    }

    @get:Rule(order = 0)
    var hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @BindValue
    @JvmField
    val appDatabase: AppDatabase = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        AppDatabase::class.java
    ).build()

    @BindValue
    @JvmField
    val placeholderService = mockk<PlaceholderService>(relaxed = true).apply {
        coEvery { getAlbums(any()) } throws HttpException(
            retrofit2.Response.error<HttpException>(
                400,
                object : ResponseBody() {
                    override fun contentLength() = 0L
                    override fun contentType() = mockk<MediaType>(relaxed = true)
                    override fun source() = mockk<BufferedSource>(relaxed = true)
                })
        )
    }

    private val albumDao = appDatabase.albumDao()

    private val dummyLaunches = listOf(
        Album(
            TEST_ID,
            TEST_TITLE
        )
    )

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
        albumDao.insertAll(dummyLaunches)

    }

    @Test
    fun whenMainActivityLaunchedAlbumsFragmentIsShown() {
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun whenMainActivityLaunchedSavedAlbumsAreDisplayed() {
        activityScenarioRule.scenario.onActivity {
            val recyclerView = it.findViewById<RecyclerView>(R.id.recyclerView)
            assertEquals(dummyLaunches.size, recyclerView.childCount)
        }

        onView(withId(R.id.recyclerView))
            .check(
                matches(
                    atPosition(
                        0,
                        withText(TEST_TITLE)

                    )
                )
            )
    }
}