package com.jerry.mvvm.repository

import com.jerry.mvvm.model.local.Favorite
import com.jerry.mvvm.model.local.FavoriteImage
import com.jerry.mvvm.model.local.FavoriteWithImage

import com.jerry.mvvm.room.FavoriteDao
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class LocalRepository  : KoinComponent {
    val favoriteDao : FavoriteDao by inject()


    suspend fun getFavorites(): List<FavoriteWithImage> {
        return  favoriteDao.getFavorites()
    }

    suspend fun getFavoriteById(id: Long): FavoriteWithImage? {
        return  favoriteDao.getFavoritebyId(id)
    }

    suspend fun insert(favorite: Favorite, images: List<FavoriteImage>)  {
        return  favoriteDao.insert(favorite, images)
    }

    suspend fun delete(id : Long)  {
        favoriteDao.deleteFavoriteImageByFavoriteId(id)
        favoriteDao.deleteFavoriteById(id)
    }
}