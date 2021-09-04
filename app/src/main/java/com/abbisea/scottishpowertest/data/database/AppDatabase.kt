package com.abbisea.scottishpowertest.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abbisea.scottishpowertest.data.models.Album
import com.abbisea.scottishpowertest.data.models.RemoteKey

@Database(entities = [Album::class, RemoteKey::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}