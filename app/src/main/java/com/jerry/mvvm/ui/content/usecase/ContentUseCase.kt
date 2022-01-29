package com.jerry.mvvm.ui.content.usecase



import com.jerry.mvvm.model.ContentDetailResponse
import com.jerry.mvvm.model.ContentListResponse
import com.jerry.mvvm.repository.NetworkRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class ContentUseCase : KoinComponent {

    val mNetworkRepository: NetworkRepository by inject()

    suspend fun getContentList(): ContentListResponse {
        return  mNetworkRepository.getContentList()
    }

    suspend fun getContentById(id: Int): ContentDetailResponse {
        return  mNetworkRepository.getContentById(id)
    }
}