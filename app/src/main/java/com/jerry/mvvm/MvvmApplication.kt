package com.jerry.mvvm

import androidx.multidex.MultiDexApplication
import com.jerry.mvvm.di.networkModule
import com.jerry.mvvm.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class MvvmApplication : MultiDexApplication(){

    override fun onCreate() {
        super.onCreate()
        startKoin {
            // Android context
            androidContext(this@MvvmApplication)
            //Module
            modules(getModules())
        }
    }

    open fun getModules() = listOf(
        networkModule,
        useCaseModule
    )
}