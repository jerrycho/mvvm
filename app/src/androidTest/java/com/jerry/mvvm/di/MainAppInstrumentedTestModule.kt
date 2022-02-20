package com.jerry.mvvm.di

fun generateTestAppComponent(baseApi: String)
        = listOf(
    networkInstrumentedTestModule(baseApi),
    databaseInstrimentedTestModule(),
    useCaseInstrumentedTestModule,

)