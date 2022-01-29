package com.jerry.mvvm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseMVVMFragment<VM : BaseViewModel, BINDING : ViewDataBinding> : BaseFragment() {

    protected lateinit var viewModel: VM
    protected lateinit var binding: BINDING

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = getViewModelInstance()

        viewModel.isShowLoading.observe(this) {
            showLoadingIndicator(it == true)
        }

        viewModel.error.observe(this) {
            it?.let{
                showErrorOKDialog(it)
            }
        }

    }



    @CallSuper
    override fun onDestroy() {
        super.onDestroy()

    }

    override fun setMainLayout(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
        return binding.root
    }

    abstract fun getViewModelInstance(): VM

}