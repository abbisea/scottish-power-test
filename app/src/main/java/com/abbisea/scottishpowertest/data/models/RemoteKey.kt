package com.abbisea.scottishpowertest.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RemoteKey(val nextKey: Int?, @PrimaryKey val id: Int = 0)