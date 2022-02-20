package com.jerry.mvvm.ui.content.viewmodel

import androidx.lifecycle.MutableLiveData
import com.jerry.mvvm.base.BaseViewModel
import com.jerry.mvvm.model.local.Favorite
import com.jerry.mvvm.model.local.FavoriteImage
import com.jerry.mvvm.model.remote.Content
import com.jerry.mvvm.model.remote.ContentListResponse
import com.jerry.mvvm.model.remote.ResponseContainer
import com.jerry.mvvm.ui.content.usecase.ContentUseCase


import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ContentListingViewModel (): BaseViewModel(), KoinComponent {

    var contentListMutableLiveData = MutableLiveData<ResponseContainer<ContentListResponse>>()
    val mContentUseCase : ContentUseCase by inject()

    private var hmHeart = HashMap<Long, Boolean>()

    init {
        if (hmHeart.isEmpty()){
            // Create a new coroutine to move the execution off the UI thread
            mIoScope.launch {
                var fCollections = mContentUseCase.getFavoriteList()
                fCollections?.forEach {
                    hmHeart.put(it.favorite.id, true)
                }
            }
        }
        getContentList()
    }

    fun getContentList(){
        mUiScope.launch {
            contentListMutableLiveData.value = ResponseContainer.loading();
            showLoadingIndicator(true)
            try {
                val data = mIoScope.async {
                    return@async mContentUseCase.getContentList()
                }.await()
                try {
                    data.items?.forEach {
                        it.liked = hmHeart.containsKey(it.id)
                    }
                    contentListMutableLiveData.value = ResponseContainer.success(data)
                } catch (e: Exception) {
                    contentListMutableLiveData.value = ResponseContainer.error(e)
                }
                showLoadingIndicator(false)
            } catch (e: Exception) {
                showLoadingIndicator(false)
                contentListMutableLiveData.value = ResponseContainer.error(e)
                error.value = e.toString()
            }
        }
    }

    fun addOrRemoveCollections(content : Content){
        mIoScope.launch {
            if (!hmHeart.containsKey(content.id)){
                doSaveFavorite(content)
                hmHeart.put(content.id!!, true)
            }
            else{
                doRemove(content.id!!)
                hmHeart.remove(content.id)
            }
        }
    }

    private fun doSaveFavorite(content : Content){
        mIoScope.launch {
            var favorite = Favorite(
                id = content.id!!,
                title = content.title,
                subtitle = content.subtitle,
                date = content.date,
            )
            var previewImageUrls: MutableList<FavoriteImage> =  arrayListOf()
            content.previewImageUrls?.forEach {
                previewImageUrls.add(
                    FavoriteImage(
                        id = null,
                        favoriteId = content.id!!,
                        url = it
                    )
                )
            }
            mContentUseCase.insertFavorite(favorite, previewImageUrls)
        }
    }

    private fun doRemove(id : Long){
        mIoScope.launch {
            mContentUseCase.deleteFavorite(id)
        }
    }
}