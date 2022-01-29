package com.jerry.mvvm.di

import com.jerry.mvvm.ui.content.usecase.ContentUseCase
import org.koin.dsl.module

//Stored all use case
val useCaseInstrumentedTestModule = module {

    factory {
        ContentUseCase()
    }
}