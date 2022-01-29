package com.jerry.mvvm.model

import java.io.Serializable

open class ContentListResponse(
    val items: List<Content>? = null,
) : Serializable