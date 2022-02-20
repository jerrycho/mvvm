package com.jerry.mvvm.room

import androidx.room.Database
import androidx.room.RoomDatabase

import com.jerry.mvvm.model.local.Favorite
import com.jerry.mvvm.model.local.FavoriteImage
import com.jerry.mvvm.model.local.FavoriteWithImage


@Database(entities = [Favorite::class, FavoriteImage::class],version = 1)
abstract class MvvmDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}

