package com.jerry.mvvm.base


import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

abstract class BaseUITest : KoinTest {

    protected lateinit var mockServer: MockWebServer


    private var mShouldStart = false

    @Before
    open fun setUp(){
        startMockServer(true)
    }

    fun mockNetworkResponse(path: String, responseCode: Int) = mockServer.enqueue(
        MockResponse()
            .setResponseCode(responseCode)
            .setBody(getJson(path))
    )

    open fun getJson(path : String) : String {
        return ""
    }

    private fun startMockServer(shouldStart:Boolean){
        if (shouldStart){
            mShouldStart = shouldStart
            mockServer = MockWebServer()
            mockServer.start()
        }
    }

    fun getMockWebServerUrl() = mockServer.url("/").toString()

    private fun stopMockServer() {
        if (mShouldStart){
            mockServer.shutdown()
        }
    }

    @After
    open fun tearDown(){
        stopMockServer()
        stopKoin()
    }
}