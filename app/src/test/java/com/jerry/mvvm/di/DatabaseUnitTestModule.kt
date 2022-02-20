package com.jerry.mvvm.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory

import com.jerry.mvvm.network.ApiService
import com.jerry.mvvm.repository.LocalRepository
import com.jerry.mvvm.repository.NetworkRepository
import com.jerry.mvvm.room.DBName
import com.jerry.mvvm.room.FavoriteDao
import com.jerry.mvvm.room.MvvmDatabase

import okhttp3.mockwebserver.MockWebServer
import org.junit.runner.manipulation.Ordering
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

fun databaseUtilTestModule() = module {

    single { ApplicationProvider.getApplicationContext<Context>() as Context }


    fun provideDatabase(context: Context): MvvmDatabase {
        return Room.inMemoryDatabaseBuilder(
            context,
            MvvmDatabase::class.java
        ).allowMainThreadQueries()
            .build()
    }

    fun provideCountriesDao(database: MvvmDatabase): FavoriteDao {
        return  database.favoriteDao()
    }

    single { provideDatabase(get()) }
    single { provideCountriesDao(get()) }

    factory {
        LocalRepository()
    }
}
