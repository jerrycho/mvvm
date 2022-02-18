package com.jerry.mvvm.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

open class BaseViewModel(
)  : ViewModel() {

    protected val job = SupervisorJob()

    open val isShowLoading: MutableLiveData<Boolean> = MutableLiveData()
    open val error: MutableLiveData<String> = MutableLiveData()

    protected val mUiScope = CoroutineScope(Dispatchers.Main + job) //Dispatchers.Main main thread , UI thread
    protected val mIoScope = CoroutineScope(Dispatchers.IO + job)// using for a job / something using long time like http call

//    val apiError = MutableLiveData<ErrorResponse>()

    fun showLoadingIndicator(showLoading: Boolean) {
        isShowLoading.postValue(showLoading)
    }

    override fun onCleared() {
        super.onCleared()
        this.job.cancel()
    }
}