package com.jerry.mvvm.room

import androidx.room.*
import com.jerry.mvvm.model.local.Favorite
import com.jerry.mvvm.model.local.FavoriteImage
import com.jerry.mvvm.model.local.FavoriteWithImage
import android.provider.SyncStateContract.Helpers.insert





@Dao
interface FavoriteDao {

    @Transaction
    @Query("SELECT * FROM $FavoriteTableName")
    fun getFavorites(): List<FavoriteWithImage>

    @Query("SELECT * FROM $FavoriteTableName WHERE id = :id ")
    fun getFavoritebyId(id: Long): FavoriteWithImage?

//    @Query("DELETE FROM $FavoriteTableName WHERE id = :id")
//    fun deleteById(id: Int)
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insert(collection: FavoriteCollection)

    @Transaction
    fun insert(favorite: Favorite, images: List<FavoriteImage>) {

        // Save rowId of inserted CompanyEntity as companyId
        val favoriteId: Long = insert(favorite)

        // Set companyId for all related employeeEntities
        for (image in images) {
            //image.favoriteId = favoriteId
            insert(image)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: Favorite): Long

    @Insert
    fun insert(favoriteImage: FavoriteImage) : Long

    @Query("DELETE FROM $FavoriteImageTableName WHERE favoriteId = :id")
    fun deleteFavoriteImageByFavoriteId(id: Long)

    @Query("DELETE FROM $FavoriteTableName WHERE id = :id")
    fun deleteFavoriteById(id: Long)
}