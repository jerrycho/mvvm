package com.jerry.mvvm.repository

import com.jerry.mvvm.network.ApiService
import com.jerry.mvvm.model.ContentDetailResponse
import com.jerry.mvvm.model.ContentListResponse
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

    class NetworkRepository : KoinComponent {

    val apiService : ApiService by inject()

    suspend fun getContentList(): ContentListResponse {
        return  apiService.getContentList()
    }

    suspend fun getContentById(id: Int): ContentDetailResponse {
        return  apiService.getContentById(id)
    }
}