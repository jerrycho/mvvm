package com.jerry.mvvm.ui

import android.os.SystemClock
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.jerry.mvvm.R
import com.jerry.mvvm.base.BaseUITest
import com.jerry.mvvm.di.generateTestAppComponent
import com.jerry.mvvm.ui.content.adapter.ContentAdapter
import com.jerry.mvvm.utils.getText
import com.jerry.mvvm.utils.recyclerItemAtPosition
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import java.net.HttpURLConnection
import java.util.regex.Pattern.matches


@RunWith(AndroidJUnit4::class)
class Main: BaseUITest(){
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Rule
    @JvmField
    var mMainActivityResult = ActivityTestRule(MainActivity::class.java, true, false)


    @Before
    fun start(){
        super.setUp()
        loadKoinModules(generateTestAppComponent(getMockWebServerUrl()).toMutableList())
    }

    override fun getJson(path : String) : String {
        if ("content_list".equals(path)){
            return "{\"items\":[{\"id\":36,\"title\":\"Article 8\",\"subtitle\":\"Article 8 subtitle with placeholder text\",\"date\":\"18/04/2013 11:48\"},{\"id\":35,\"title\":\"Article 7\",\"subtitle\":\"Article 7 subtitle with placeholder text\",\"date\":\"17/04/2013 11:48\"},{\"id\":34,\"title\":\"Article 6\",\"subtitle\":\"Article 6 subtitle with placeholder text\",\"date\":\"16/04/2013 11:48\"},{\"id\":33,\"title\":\"Article 5\",\"subtitle\":\"Article 5 subtitle with placeholder text\",\"date\":\"15/04/2013 11:48\"},{\"id\":32,\"title\":\"Article 4\",\"subtitle\":\"Article 4 subtitle with placeholder text\",\"date\":\"14/04/2013 11:48\"},{\"id\":31,\"title\":\"Article 3\",\"subtitle\":\"Article 3 subtitle with placeholder text\",\"date\":\"13/04/2013 11:48\"},{\"id\":30,\"title\":\"Article 2\",\"subtitle\":\"Article 2 subtitle with placeholder text\",\"date\":\"12/04/2013 11:48\"},{\"id\":29,\"title\":\"Article 1\",\"subtitle\":\"Article 1 subtitle with placeholder text\",\"date\":\"11/04/2013 11:48\"}]}"
        }
        else {
            return "{\"item\":{\"id\":36,\"title\":\"Article 8\",\"subtitle\":\"Article 8 subtitle with placeholder text\",\"body\":\"Article 8 Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. Article 8\",\"date\":\"18/04/2013 11:48\"}}"
        }
    }


    @Test
    fun test_recyclerview_elements_for_expected_response() {
        mMainActivityResult.launchActivity(null)

        mockNetworkResponse("content_list", HttpURLConnection.HTTP_OK)

        //Wait for MockWebServer to get back with response
        SystemClock.sleep(1000)

        //Check if item at 0th position is having 0th element in json
        Espresso.onView(withId(R.id.rvContent))
            .check(
                ViewAssertions.matches(
                    recyclerItemAtPosition(
                        0,
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Article 8"))
                    )
                )
            )

        //Scroll to last
        Espresso.onView(withId(R.id.rvContent)).perform(
            RecyclerViewActions.scrollToPosition<ContentAdapter.ContentViewHolder>(7))

        //Check 7 item
        Espresso.onView(withId(R.id.rvContent))
            .check(
                ViewAssertions.matches(
                    recyclerItemAtPosition(
                        7,
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Article 1"))
                    )
                )
            )

        mockNetworkResponse("detail_36", HttpURLConnection.HTTP_OK)

        clickOnButtonAtRow(0)

        SystemClock.sleep(1000)

    }

    private fun clickOnButtonAtRow(position: Int) {
        Espresso.onView(ViewMatchers.withId(R.id.rvContent)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>
            (position, ClickOnButtonView()))
    }

    inner class ClickOnButtonView : ViewAction {
        internal var click = ViewActions.click()

        override fun getConstraints(): Matcher<View> {
            return click.constraints
        }

        override fun getDescription(): String {
            return " click on custom button view"
        }

        override fun perform(uiController: UiController, view: View) {
            //btnClickMe -> Custom row item view button
            click.perform(uiController, view.findViewById(R.id.detailLayout))
        }
    }
}