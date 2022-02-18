package com.jerry.mvvm.ui.content

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe

import com.jerry.mvvm.R
import com.jerry.mvvm.base.BaseMVVMFragment
import com.jerry.mvvm.databinding.FragContentDetailBinding
import com.jerry.mvvm.model.ContentDetail
import com.jerry.mvvm.ui.content.usecase.ContentUseCase
import com.jerry.mvvm.ui.content.viewmodel.ContentDetailViewModel
import com.jerry.mvvm.ui.content.viewmodel.ContentListingViewModel

import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.android.inject

private const val KEY_SELECTED_ID = "KEY_SELECTED_ID"

class ContentDetailFragment : BaseMVVMFragment<FragContentDetailBinding>() {

    val viewModel: ContentDetailViewModel by viewModels()
    val mContentUseCase : ContentUseCase by inject()

    private var id: Int? = null

    companion object {
        fun createBundle(id: Int) = Bundle().apply {
            putInt(KEY_SELECTED_ID, id)
        }
    }


    override fun getViewModelInstance(): ContentDetailViewModel {
        //return ContentDetailViewModel(Dispatchers.Main, Dispatchers.IO, mContentUseCase)
        return viewModel
    }


    override fun getLayoutResId(): Int {
        return R.layout.frag_content_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(KEY_SELECTED_ID)

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.showTitlabarBackIcon(true)
        getDetil()
        viewModel.contentDetailMutableLiveData.observe(viewLifecycleOwner) {
            it?.let {
                updateUI(it.response?.item)
            }
        }
    }

    fun getDetil(){
        id?.let{
            viewModel.getContentById(id)
        }
    }

    fun updateUI(item : ContentDetail?){
        item?.let {
            if (item.id!=null)
                binding.tvId.text = item.id.toString()
            else
                binding.tvId.text = "---"
            binding.tvTitle.text = item.title?: "---"
            binding.tvSubTitle.text = item.subtitle?: "---"
            binding.tvBody.text = item.body?: "---"
            binding.tvDate.text = item.date?: "---"
        }
    }

    override fun showBack(): Boolean {
        return true
    }

    override fun onRetryButtonClicked() {
        getDetil()
    }
}