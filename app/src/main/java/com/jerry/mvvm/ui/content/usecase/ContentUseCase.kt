package com.jerry.mvvm.ui.content.usecase



import com.jerry.mvvm.model.local.Favorite
import com.jerry.mvvm.model.local.FavoriteImage
import com.jerry.mvvm.model.local.FavoriteWithImage
import com.jerry.mvvm.model.remote.ContentDetailResponse
import com.jerry.mvvm.model.remote.ContentListResponse
import com.jerry.mvvm.repository.LocalRepository
import com.jerry.mvvm.repository.NetworkRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class ContentUseCase : KoinComponent {

    val mNetworkRepository: NetworkRepository by inject()
    val mLocalRepository: LocalRepository by inject()


    suspend fun getContentList(): ContentListResponse {
        return  mNetworkRepository.getContentList()
    }

    suspend fun getContentById(id: Long): ContentDetailResponse {
        return  mNetworkRepository.getContentById(id)
    }

    suspend fun getFavoriteList() : List<FavoriteWithImage> {
        return mLocalRepository.getFavorites()
    }
    suspend fun getFavoriteById(id:Long) : FavoriteWithImage? {
        return mLocalRepository.getFavoriteById(id)
    }
    suspend fun insertFavorite(favorite: Favorite, images: List<FavoriteImage>){
        mLocalRepository.insert(favorite, images)
    }
    suspend fun deleteFavorite(id : Long){
        mLocalRepository.delete(id)
    }
}