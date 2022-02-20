package com.jerry.mvvm.model.local

import androidx.room.Embedded
import androidx.room.Relation

data class FavoriteWithImage(
    @Embedded val favorite: Favorite,
    @Relation(
        parentColumn = "id",
        entityColumn = "favoriteId"
    )
    val previewImageUrls: List<FavoriteImage>
)
