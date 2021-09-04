package com.abbisea.scottishpowertest.hiltmodules

import com.abbisea.scottishpowertest.data.api.PlaceholderService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providePlaceholderService(): PlaceholderService =
        PlaceholderService.create()
}