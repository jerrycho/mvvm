package com.jerry.mvvm.di

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory

import com.jerry.mvvm.network.ApiService
import com.jerry.mvvm.repository.NetworkRepository

import okhttp3.mockwebserver.MockWebServer
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun networkUtilTestModule(baseTestApi: String) = module {
    single {
        Retrofit.Builder()
            .baseUrl(baseTestApi)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }
    factory{ get<Retrofit>().create(ApiService::class.java) }

    factory {
        MockWebServer()
    }

    factory {
        NetworkRepository()
    }
}
