package com.abbisea.scottishpowertest.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abbisea.scottishpowertest.data.models.Album

@Dao
interface AlbumDao {

    @Query("SELECT * FROM Album ORDER BY title ASC")
    fun getAllAlbums(): PagingSource<Int, Album>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<Album>)

    @Query("DELETE FROM Album")
    fun clearAll()
}