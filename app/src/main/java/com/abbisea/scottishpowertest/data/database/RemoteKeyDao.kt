package com.abbisea.scottishpowertest.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abbisea.scottishpowertest.data.models.RemoteKey

@Dao
interface RemoteKeyDao {

    @Query("SELECT * FROM RemoteKey")
    fun getKey(): RemoteKey

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertKey(item: RemoteKey)

    @Query("DELETE FROM RemoteKey")
    fun clearAll()
}