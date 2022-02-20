package com.jerry.mvvm.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.jerry.mvvm.base.BaseTest
import com.jerry.mvvm.di.mainAppUnitTestModule
import com.jerry.mvvm.model.local.Favorite
import com.jerry.mvvm.model.local.FavoriteImage
import com.jerry.mvvm.model.local.FavoriteWithImage
import com.jerry.mvvm.model.remote.ContentDetailResponse
import com.jerry.mvvm.model.remote.ResponseContainer
import com.jerry.mvvm.room.MvvmDatabase
import com.jerry.mvvm.ui.content.usecase.ContentUseCase
import com.jerry.mvvm.ui.content.viewmodel.ContentDetailViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import io.mockk.slot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.GlobalContext
import org.koin.test.inject

@RunWith(JUnit4::class)
class ContentDetailViewModelCaseTest :  BaseTest() {

    //protected val database: MvvmDatabase by inject()

    @MockK
    lateinit var database: MvvmDatabase

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var mContentDetailViewModel: ContentDetailViewModel

    val mDispatcher = Dispatchers.Unconfined

    @MockK
    lateinit var mContentUseCase: ContentUseCase

    @Before
    fun start() {
        super.setUp()
        MockKAnnotations.init(this)

        GlobalContext.startKoin { modules(mainAppUnitTestModule(getMockWebServerUrl())) }

    }

    @Test
    fun test_load_detail_view_expected_value() {
        mContentDetailViewModel = ContentDetailViewModel()

        val json =
            "{\"item\":{\"id\":36,\"title\":\"Article 8\",\"subtitle\":\"Article 8 subtitle with placeholder text\",\"body\":\"Article 8 Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Article 8\",\"date\":\"18/04/2013 11:48\"}}"
        var jsonObj = Gson().fromJson(json, ContentDetailResponse::class.java)

        //Make sure use case returns expected response when called
        coEvery { mContentUseCase.getContentById(1) } returns jsonObj

        mContentDetailViewModel.contentDetailMutableLiveData.value =
            ResponseContainer.success(jsonObj)

        assert(mContentDetailViewModel.contentDetailMutableLiveData.value != null)
        assert(
            mContentDetailViewModel.contentDetailMutableLiveData.value!!.status
                    == ResponseContainer.STATUS.SUCCESS
        )
        assert(
            mContentDetailViewModel.contentDetailMutableLiveData.value!!.response != null
        )
        assert(
            mContentDetailViewModel.contentDetailMutableLiveData.value!!.response!!.item != null
        )
    }

}