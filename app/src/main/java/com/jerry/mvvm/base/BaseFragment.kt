package com.jerry.mvvm.base

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

import com.jerry.mvvm.ui.MainActivity
import com.jerry.mvvm.R


abstract class BaseFragment : Fragment() {

    protected lateinit var mainActivity: MainActivity
    protected lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
    }

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return setMainLayout(inflater, container)
    }


    override fun onResume() {
        super.onResume()
        mainActivity.showTitlabarBackIcon(showBack())
    }

    @CallSuper
    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
    }

    fun showLoadingIndicator(isShowLoading: Boolean) {
        mainActivity.showLoading(isShowLoading)
    }

    open fun setMainLayout(inflater: LayoutInflater, container: ViewGroup?): View {
        return inflater.inflate(getLayoutResId(), container, false)
    }

    @LayoutRes
    abstract fun getLayoutResId(): Int

    open fun showBack(): Boolean = false

    open fun showErrorOKDialog(message :String) {
        mainActivity?.let {
            MaterialAlertDialogBuilder(mainActivity)
                .setMessage(message)
                .setPositiveButton(R.string.retry) { dialog, which ->
                    dialog.dismiss()
                    onRetryButtonClicked()
                }
                .setNegativeButton(android.R.string.cancel, null)
                .setCancelable(false)
                .show()
        }
    }

    abstract fun onRetryButtonClicked()
}