package com.jerry.mvvm.app

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner

class MvvmInstrumentationRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader,
                                className: String,
                                context: Context
    ): Application {
        return super.newApplication(cl,
            TestMvvmApplicationApp::class.java.name,
            context)
    }
}