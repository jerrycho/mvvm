package com.cookpad.hiring.android.ui.listener

import com.jerry.mvvm.model.remote.Content


interface OnItemListener {
    fun onHeartClicked(item : Content)
    fun onItemClicked(item: Content)
}