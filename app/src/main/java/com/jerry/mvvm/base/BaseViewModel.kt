package com.jerry.mvvm.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

open class BaseViewModel(
    mainDispatcher: CoroutineDispatcher,
    ioDispatcher: CoroutineDispatcher,
)  : ViewModel() {

    protected val job = SupervisorJob()

    open val isShowLoading: MutableLiveData<Boolean> = MutableLiveData()
    open val error: MutableLiveData<String> = MutableLiveData()


    protected val mUiScope = CoroutineScope(mainDispatcher + job)
    protected val mIoScope = CoroutineScope(ioDispatcher + job)

//    val apiError = MutableLiveData<ErrorResponse>()

    fun showLoadingIndicator(showLoading: Boolean) {
        isShowLoading.postValue(showLoading)
    }

    override fun onCleared() {
        super.onCleared()
        this.job.cancel()
    }
}