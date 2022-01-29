package com.jerry.mvvm.model

import java.io.Serializable


data class ContentDetail (
    val body: String? = null,
) : Content(), Serializable

