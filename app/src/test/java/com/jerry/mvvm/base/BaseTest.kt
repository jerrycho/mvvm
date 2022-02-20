package com.jerry.mvvm.base

import android.content.res.Resources
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jerry.mvvm.room.MvvmDatabase
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import java.io.File


abstract class BaseTest : KoinTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mMockServerInstance: MockWebServer

    //val mvvmDatabase by inject<MvvmDatabase>()

    private var mShouldStart = false

    @Before
    open fun setUp(){
        startMockServer(true)
    }

    fun mockNetworkResponse(path: String, responseCode: Int) = mMockServerInstance.enqueue(
        MockResponse()
            .setResponseCode(responseCode)
            .setBody(getJson(path))
    )

    open fun getJson(path : String) : String {
        return ""
    }


    /**
     * Start Mockwebserver
     */
    private fun startMockServer(shouldStart:Boolean){
        if (shouldStart){
            mShouldStart = shouldStart
            mMockServerInstance = MockWebServer()
            mMockServerInstance.start()
        }
    }

    /**
     * Set Mockwebserver url
     */
    fun getMockWebServerUrl() = mMockServerInstance.url("/").toString()

    /**
     * Stop Mockwebserver
     */
    private fun stopMockServer() {
        if (mShouldStart){
            mMockServerInstance.shutdown()
        }
    }

    @After
    open fun tearDown(){

        //Stop Mock server
        stopMockServer()
        //Stop Koin as well
        stopKoin()
    }
}