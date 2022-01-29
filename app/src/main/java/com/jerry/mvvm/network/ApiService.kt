package com.jerry.mvvm.network

import com.jerry.mvvm.model.ContentDetailResponse
import com.jerry.mvvm.model.ContentListResponse
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {

    @GET("/test/native/contentList.json")
    suspend fun getContentList(): ContentListResponse

    @GET("/test/native/content/{contentId}.json")
    suspend fun getContentById(@Path("contentId") contentId: Int): ContentDetailResponse

}