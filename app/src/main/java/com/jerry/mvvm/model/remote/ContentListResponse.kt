package com.jerry.mvvm.model.remote

import java.io.Serializable

open class ContentListResponse(
    val items: List<Content>? = null,
) : Serializable