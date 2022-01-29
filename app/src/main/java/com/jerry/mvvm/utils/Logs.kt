package com.jerry.mvvm.utils

import android.util.Log
import com.google.gson.Gson
import com.jerry.mvvm.BuildConfig


object Logs {

    private val TAG = "----->"

    private val isLog = BuildConfig.DEBUG

    fun d(any: Any?) {
        if (isLog) d(TAG, any)
    }

    fun w(any: Any?) {
        if (isLog)w(TAG, any)
    }


    fun e(any: Any?) {
        if (isLog) e(TAG, any)
    }

    fun d(tag: String, any: Any?) {
        if (isLog)
            Log.d(tag, any.toString())
    }

    fun w(tag: String, any: Any?) {
        if (isLog)
            Log.w(tag, any.toString())

    }

    fun e(tag: String, any: Any?) {
        if (isLog)
            Log.e(tag, any.toString())

    }

    fun toJson(any: Any?) {
        if (isLog)
            toJson(TAG, any)
    }

    fun toJson(tag: String, any: Any?) {
        if (isLog)
            Log.w(tag, Gson().toJson(any))
    }
}