package com.jerry.mvvm.model.remote

import java.io.Serializable


data class ContentDetail (
    val body: String? = null,
) : Content(), Serializable

