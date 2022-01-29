package com.jerry.mvvm.ui.content.viewmodel

import androidx.lifecycle.MutableLiveData
import com.jerry.mvvm.base.BaseViewModel
import com.jerry.mvvm.model.ContentListResponse
import com.jerry.mvvm.model.ResponseContainer
import com.jerry.mvvm.ui.content.usecase.ContentUseCase


import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class ContentListingViewModel (
    mainDispatcher: CoroutineDispatcher,
    ioDispatcher: CoroutineDispatcher,
    private val useCase: ContentUseCase
)
    : BaseViewModel(mainDispatcher, ioDispatcher,), KoinComponent {

    var contentListMutableLiveData = MutableLiveData<ResponseContainer<ContentListResponse>>()

    init {
        getContentList()
    }

    fun getContentList(){
        mUiScope.launch {
            contentListMutableLiveData.value = ResponseContainer.loading();
            showLoadingIndicator(true)
            try {
                val data = mIoScope.async {
                    return@async useCase.getContentList()
                }.await()
                try {
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
}