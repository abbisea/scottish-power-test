package com.abbisea.scottishpowertest.data.api

import androidx.paging.ExperimentalPagingApi
import com.abbisea.scottishpowertest.data.AlbumRemoteMediator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceholderService {

    @ExperimentalPagingApi
    @GET("/albums")
    suspend fun getAlbums(
        @Query("_page") page: Int,
        @Query("_limit") limit: Int = AlbumRemoteMediator.PAGE_SIZE,
        @Query("_sort") sort: String = "title",
        @Query("_order") order: String = "asc"
    ): List<AlbumsDTO>

    companion object {
        private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

        fun create(): PlaceholderService {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PlaceholderService::class.java)
        }
    }
}
