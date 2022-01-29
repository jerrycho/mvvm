package com.jerry.mvvm.ui.content.viewmodel

import androidx.lifecycle.MutableLiveData
import com.jerry.mvvm.base.BaseViewModel
import com.jerry.mvvm.model.ContentDetailResponse
import com.jerry.mvvm.model.ContentListResponse
import com.jerry.mvvm.model.ResponseContainer
import com.jerry.mvvm.ui.content.usecase.ContentUseCase


import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class ContentDetailViewModel (
    mainDispatcher: CoroutineDispatcher,
    ioDispatcher: CoroutineDispatcher,
    private val useCase: ContentUseCase
)
    : BaseViewModel(mainDispatcher, ioDispatcher,), KoinComponent {

    open var contentDetailMutableLiveData = MutableLiveData<ResponseContainer<ContentDetailResponse>>()

    fun getContentById(id: Int?){
        mUiScope.launch {
            contentDetailMutableLiveData.value = ResponseContainer.loading();
            showLoadingIndicator(true)
            try {
                val data = mIoScope.async {
                    return@async useCase.getContentById(id!!)
                }.await()
                try {
                    contentDetailMutableLiveData.value = ResponseContainer.success(data)
                } catch (e: Exception) {
                    contentDetailMutableLiveData.value = ResponseContainer.error(e)
                }
                showLoadingIndicator(false)
            } catch (e: Exception) {
                showLoadingIndicator(false)
                contentDetailMutableLiveData.value = ResponseContainer.error(e)
                error.value = e.toString()
            }
        }
    }


}