package com.jerry.mvvm.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.jerry.mvvm.room.FavoriteTableName


@Entity(tableName = FavoriteTableName)
data class Favorite (
    @PrimaryKey var id: Long,
    var title: String?,
    var subtitle: String?,
    var date: String?,

//    //TODO
//    @TypeConverters(Converters::class)
//    var previewImageUrls: List<String> = emptyList()
)
