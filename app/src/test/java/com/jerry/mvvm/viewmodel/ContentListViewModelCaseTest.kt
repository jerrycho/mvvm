package com.jerry.mvvm.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.jerry.mvvm.base.BaseTest
import com.jerry.mvvm.di.mainAppUnitTestModule
import com.jerry.mvvm.model.remote.ContentListResponse
import com.jerry.mvvm.model.remote.ResponseContainer
import com.jerry.mvvm.ui.content.usecase.ContentUseCase
import com.jerry.mvvm.ui.content.viewmodel.ContentListingViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.GlobalContext.startKoin

@RunWith(JUnit4::class)
class ContentListViewModelCaseTest : BaseTest(){

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var mContentListingViewModel: ContentListingViewModel

    val mDispatcher = Dispatchers.Unconfined

    @MockK
    lateinit var mContentUseCase: ContentUseCase

    @Before
    fun start(){
        super.setUp()
        MockKAnnotations.init(this)
        startKoin{ modules(mainAppUnitTestModule(getMockWebServerUrl()))}
    }

    @Test
    fun test_load_list_view_expected_value(){
        mContentListingViewModel = ContentListingViewModel()

        val json = "{\"items\":[{\"id\":36,\"title\":\"Article 8\",\"subtitle\":\"Article 8 subtitle with placeholder text\",\"date\":\"18/04/2013 11:48\"},{\"id\":35,\"title\":\"Article 7\",\"subtitle\":\"Article 7 subtitle with placeholder text\",\"date\":\"17/04/2013 11:48\"},{\"id\":34,\"title\":\"Article 6\",\"subtitle\":\"Article 6 subtitle with placeholder text\",\"date\":\"16/04/2013 11:48\"},{\"id\":33,\"title\":\"Article 5\",\"subtitle\":\"Article 5 subtitle with placeholder text\",\"date\":\"15/04/2013 11:48\"},{\"id\":32,\"title\":\"Article 4\",\"subtitle\":\"Article 4 subtitle with placeholder text\",\"date\":\"14/04/2013 11:48\"},{\"id\":31,\"title\":\"Article 3\",\"subtitle\":\"Article 3 subtitle with placeholder text\",\"date\":\"13/04/2013 11:48\"},{\"id\":30,\"title\":\"Article 2\",\"subtitle\":\"Article 2 subtitle with placeholder text\",\"date\":\"12/04/2013 11:48\"},{\"id\":29,\"title\":\"Article 1\",\"subtitle\":\"Article 1 subtitle with placeholder text\",\"date\":\"11/04/2013 11:48\"}]}";

        var jsonObj = Gson().fromJson(json, ContentListResponse::class.java)

        //Make sure use case returns expected response when called
        coEvery { mContentUseCase.getContentList() } returns jsonObj

        mContentListingViewModel.contentListMutableLiveData.value = ResponseContainer.success(jsonObj)

        assert(mContentListingViewModel.contentListMutableLiveData.value != null)
        assert(
            mContentListingViewModel.contentListMutableLiveData.value!!.status
                    == ResponseContainer.STATUS.SUCCESS
        )
        assert(
            mContentListingViewModel.contentListMutableLiveData.value!!.response!=null
        )
        assert(
            mContentListingViewModel.contentListMutableLiveData.value!!.response!!.items!!.size==8
        )
    }
}