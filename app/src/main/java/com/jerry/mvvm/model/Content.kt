package com.jerry.mvvm.model

import java.io.Serializable

open class Content(
    val id: Int? = null,
    val title: String? = null,
    val subtitle: String? = null,
    val date: String? = null,

) : Serializable