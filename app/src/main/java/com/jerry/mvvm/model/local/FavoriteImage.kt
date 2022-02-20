package com.jerry.mvvm.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jerry.mvvm.room.FavoriteImageTableName

@Entity(tableName = FavoriteImageTableName)
data class FavoriteImage(
    @PrimaryKey (autoGenerate = true)
    var id: Long?,
    var favoriteId: Long,
    var url: String
)