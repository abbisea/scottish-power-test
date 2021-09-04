package com.abbisea.scottishpowertest.hiltmodules

import android.content.Context
import androidx.room.Room
import com.abbisea.scottishpowertest.data.database.AlbumDao
import com.abbisea.scottishpowertest.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideAlbumDao(appDatabase: AppDatabase): AlbumDao =
        appDatabase.albumDao()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "launches_db"
        ).build()
}