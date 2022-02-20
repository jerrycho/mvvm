package com.jerry.mvvm.ui.content

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.cookpad.hiring.android.ui.listener.OnItemListener
import com.jerry.mvvm.model.remote.Content
import com.jerry.mvvm.ui.content.adapter.ContentAdapter
import com.jerry.mvvm.ui.content.viewmodel.ContentListingViewModel
import com.jerry.mvvm.R
import com.jerry.mvvm.base.BaseMVVMFragment
import com.jerry.mvvm.databinding.FragContentListBinding

//<ContentListingViewModel, FragContentListBinding>
class ContentListFragment : BaseMVVMFragment<FragContentListBinding>() {

    val viewModel: ContentListingViewModel by viewModels()

    lateinit var contentAdapter: ContentAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvContent.apply {
            contentAdapter = ContentAdapter()
            contentAdapter.setListener(object : OnItemListener {
                override fun onItemClicked(item: Content) {
                    doNavToDetail(item)
                }

                override fun  onHeartClicked(item : Content){
                    viewModel.addOrRemoveCollections(item)
                }
            })

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