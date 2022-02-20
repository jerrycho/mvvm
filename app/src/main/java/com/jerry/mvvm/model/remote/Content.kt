package com.jerry.mvvm.model.remote

import java.io.Serializable

open class Content(
    val id: Long? = null,
    val title: String? = null,
    val subtitle: String? = null,
    val date: String? = null,
    val previewImageUrls: List<String> = emptyList(),
    var liked : Boolean = false
) : Serializable