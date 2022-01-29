package com.jerry.mvvm.app

import com.jerry.mvvm.MvvmApplication
import org.koin.core.module.Module

class TestMvvmApplicationApp : MvvmApplication() {
    override fun getModules() = listOf<Module>()
}