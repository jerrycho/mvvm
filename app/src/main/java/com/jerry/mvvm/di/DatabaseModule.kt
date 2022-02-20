package com.jerry.mvvm.di

import android.app.Application
import androidx.room.Room
import com.jerry.mvvm.repository.LocalRepository
import com.jerry.mvvm.repository.NetworkRepository
import com.jerry.mvvm.room.DBName
import com.jerry.mvvm.room.FavoriteDao
import com.jerry.mvvm.room.MvvmDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(application: Application): MvvmDatabase {
        return Room.databaseBuilder(application, MvvmDatabase::class.java, DBName)
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideCountriesDao(database: MvvmDatabase): FavoriteDao {
        return  database.favoriteDao()
    }

    single { provideDatabase(androidApplication()) }
    single { provideCountriesDao(get()) }

    factory {
        LocalRepository()
    }
}