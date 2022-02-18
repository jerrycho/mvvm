package com.jerry.mvvm.ui.content

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.jerry.mvvm.model.Content
import com.jerry.mvvm.ui.content.adapter.ContentAdapter
import com.jerry.mvvm.ui.content.usecase.ContentUseCase
import com.jerry.mvvm.ui.content.viewmodel.ContentListingViewModel
import com.jerry.mvvm.R
import com.jerry.mvvm.base.BaseMVVMFragment
import com.jerry.mvvm.databinding.FragContentListBinding

import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.android.inject

//<ContentListingViewModel, FragContentListBinding>
class ContentListFragment : BaseMVVMFragment<FragContentListBinding>() {

    val viewModel: ContentListingViewModel by viewModels()

    private var contentAdapter: ContentAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contentAdapter = ContentAdapter(object : ContentAdapter.OnItemClickListener {
            override fun onItemClicked(item: Content) {
                doNavToDetail(item)
            }
        })

        binding.rvContent.apply {
            adapter = contentAdapter
            itemAnimator = null
            addItemDecoration(
                DividerItemDecoration(
                    requireContext().applicationContext,
                    DividerItemDecoration.VERTICAL
                )
            )
        }

        viewModel.contentListMutableLiveData.observe(viewLifecycleOwner) {
            contentAdapter?.setContentList(it?.response?.items)
        }

        binding.eBtnRefresh.setOnClickListener {
            onRetryButtonClicked()
        }

    }

    fun doNavToDetail(item: Content){
        item?.let {
            NavHostFragment.findNavController(this).navigate(
                R.id.action_contentListFragment_to_contentDetailFragment,
                ContentDetailFragment.createBundle(item.id!!)
            )
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.frag_content_list
    }

    override fun getViewModelInstance(): ContentListingViewModel {
        return viewModel
        //return ContentListingViewModel(Dispatchers.Main, Dispatchers.IO, mContentUseCase)
    }

    override fun onRetryButtonClicked() {
        viewModel.getContentList()
    }

}