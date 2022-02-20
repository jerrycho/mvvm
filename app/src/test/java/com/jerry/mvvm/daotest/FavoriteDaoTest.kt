package com.jerry.mvvm.daotest

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.jerry.mvvm.base.BaseTest
import com.jerry.mvvm.model.local.Favorite
import com.jerry.mvvm.model.local.FavoriteImage
import com.jerry.mvvm.model.local.FavoriteWithImage
import com.jerry.mvvm.room.FavoriteDao
import com.jerry.mvvm.room.MvvmDatabase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.koin.androidApplication
import org.koin.test.KoinTest
import org.koin.test.inject

class FavoriteDaoTest : BaseTest() {

    val favoriteDao by inject<FavoriteDao>()

    @Test
    fun firstTest() {
        var favorite = Favorite(
            id = 1,
            title = "this is title",
            subtitle = "this is subTitle",
            date = "this is date",
        )
        var previewImageUrls: MutableList<FavoriteImage> =  arrayListOf()

        previewImageUrls.add(
            FavoriteImage(
                id = null,
                favoriteId = 1,
                url = "this is url1"
            )
        )
        favoriteDao.insert(favorite, previewImageUrls)
        //weatherDao.loadAll()
    }
}