package com.jerry.mvvm.model


class ResponseContainer<T>(
    val status: STATUS,
    val response: T? = null,
    val error: Throwable? = null,
)
{
    enum class STATUS {
        SUCCESS, LOADING, ERROR
    }

    companion object {
        fun <T> loading() = ResponseContainer<T>(STATUS.LOADING)
        fun <T> success (data: T) = ResponseContainer<T>(STATUS.SUCCESS, data)
        fun <T> error(error:Throwable) = ResponseContainer<T>(STATUS.ERROR, null, error)
    }
}