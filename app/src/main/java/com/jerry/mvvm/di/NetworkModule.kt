package com.jerry.mvvm.di

import com.google.gson.GsonBuilder
import com.jerry.mvvm.BuildConfig
import com.jerry.mvvm.constants.TIME_OUT
import com.jerry.mvvm.network.ApiService
import com.jerry.mvvm.repository.NetworkRepository
import com.jerry.mvvm.utils.Logs
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

val networkModule = module {

    single {

        //basic setting
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val fileLogger = HttpLoggingInterceptor.Logger() {
            val timeFormat = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
            val currentTime = timeFormat.format(Date())
            Logs.d("NetworkModule", "$currentTime - $it")
        }
        val httpLoggingInterceptor = HttpLoggingInterceptor(fileLogger)
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .baseUrl(BuildConfig.BASE_URL).build()
    }

    factory{ get<Retrofit>().create(ApiService::class.java) }

    factory {
        NetworkRepository()
    }
}