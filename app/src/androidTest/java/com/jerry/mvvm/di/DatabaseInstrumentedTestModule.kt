package com.jerry.mvvm.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory

import com.jerry.mvvm.network.ApiService
import com.jerry.mvvm.repository.LocalRepository
import com.jerry.mvvm.room.FavoriteDao
import com.jerry.mvvm.room.MvvmDatabase

import org.koin.dsl.module

fun databaseInstrimentedTestModule() = module {

    // Test Room Database
    single {
        // In-Memory database config
        Room.inMemoryDatabaseBuilder(get(), MvvmDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }
    // Test Birds DAO
    single { get<MvvmDatabase>().favoriteDao() }

    factory {
        LocalRepository()
    }
}
