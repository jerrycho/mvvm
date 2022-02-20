package com.jerry.mvvm.di

fun mainAppUnitTestModule(baseApi: String) = listOf(
    networkUtilTestModule(baseApi),
    databaseUtilTestModule()
)
