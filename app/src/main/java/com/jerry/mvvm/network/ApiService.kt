package com.jerry.mvvm.network

import com.jerry.mvvm.model.ContentDetailResponse
import com.jerry.mvvm.model.ContentListResponse
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {

    @GET("/jerrycho/mvvm/main/json/content_list.json")
    suspend fun getContentList(): ContentListResponse

    @GET("/jerrycho/mvvm/main/json/detail_{contentId}.json")
    suspend fun getContentById(@Path("contentId") contentId: Int): ContentDetailResponse

}