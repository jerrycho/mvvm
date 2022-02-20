package com.jerry.mvvm.daotest

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4

import com.jerry.mvvm.model.local.Favorite
import com.jerry.mvvm.model.local.FavoriteImage
import com.jerry.mvvm.model.local.FavoriteWithImage
import com.jerry.mvvm.room.FavoriteDao
import com.jerry.mvvm.room.MvvmDatabase
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import java.io.IOException

/*
    https://developer.android.com/training/data-storage/room/testing-db
    cannot test with unit test
 */

@RunWith(AndroidJUnit4::class)
class FavoriteDaoTest : TestCase() {

    private lateinit var favoriteDao : FavoriteDao
    private lateinit var db : MvvmDatabase

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MvvmDatabase::class.java
        ).build()
        favoriteDao = db.favoriteDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun test_with_dao() = runBlocking {
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
        //insert
        favoriteDao.insert(favorite, previewImageUrls)

        //get by ID
        var mFavoriteWithImage : FavoriteWithImage? = favoriteDao.getFavoritebyId(1)
        assert(mFavoriteWithImage!=null)
        assertEquals(mFavoriteWithImage!!.favorite.id, 1)

        //get All
        var mFavoriteWithImages  = favoriteDao.getFavorites()
        assert(mFavoriteWithImages !=null)
        assertEquals(mFavoriteWithImages .size , 1)

        //delete
        favoriteDao.deleteFavoriteById(1)
        mFavoriteWithImage  = favoriteDao.getFavoritebyId(1)
        assert(mFavoriteWithImage==null)

    }


//    @Test
//    fun firstTest() {
//        var favorite = Favorite(
//            id = 1,
//            title = "this is title",
//            subtitle = "this is subTitle",
//            date = "this is date",
//        )
//        var previewImageUrls: MutableList<FavoriteImage> =  arrayListOf()
//
//        previewImageUrls.add(
//            FavoriteImage(
//                id = null,
//                favoriteId = 1,
//                url = "this is url1"
//            )
//        )
//        favoriteDao.insert(favorite, previewImageUrls)
//        //weatherDao.loadAll()
//    }
}