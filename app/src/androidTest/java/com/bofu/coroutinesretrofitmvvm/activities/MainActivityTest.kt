package com.bofu.coroutinesretrofitmvvm.activities

import android.content.Context
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.bofu.coroutinesretrofitmvvm.R
import com.bofu.coroutinesretrofitmvvm.adapters.MainAdapter
import com.bofu.coroutinesretrofitmvvm.extensions.isConnectedToNetwork
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{

    @get: Rule
    val mainActivityRole = ActivityScenarioRule(MainActivity::class.java)

    private val context: Context = InstrumentationRegistry.getInstrumentation().context
    private val isConnected = context.isConnectedToNetwork()

    @Test
    fun test_activity_in_view(){
        onView(withId(R.id.activity_main)).check(matches(isDisplayed()))
    }

    @Test
    fun test_recyclerview_in_view(){
        onView(withId(R.id.main_recyclerview)).check(matches(isDisplayed()))
    }

    @Test
    fun test_on_options_item_selected(){
        onView(withId(R.id.filter)).perform(click())
    }

    @Test
    fun test_visibility_of_element_on_condition_of_connection(){

        if(!isConnected){
            onView(withId(R.id.no_connection_view_layout)).check(
                matches(
                    withEffectiveVisibility(
                        Visibility.VISIBLE
                    )
                )
            )
        } else {
            onView(withId(R.id.main_recyclerview)).check(
                matches(
                    withEffectiveVisibility(
                        Visibility.VISIBLE
                    )
                )
            )
        }
    }

    @Test
    fun test_recyclerview_on_click(){
        if(isConnected){
            runBlocking {
                delay(2000L)
                onView(withId(R.id.main_recyclerview)).perform(actionOnItemAtPosition<MainAdapter.MainHolder>(0,click()))
                onView(withId(R.id.main_recyclerview)).perform(actionOnItemAtPosition<MainAdapter.MainHolder>(1,click()))
                onView(withId(R.id.main_recyclerview)).perform(actionOnItemAtPosition<MainAdapter.MainHolder>(2,click()))
                onView(withId(R.id.main_recyclerview)).perform(actionOnItemAtPosition<MainAdapter.MainHolder>(3,click()))
                onView(withId(R.id.main_recyclerview)).perform(actionOnItemAtPosition<MainAdapter.MainHolder>(4,click()))
                onView(withId(R.id.main_recyclerview)).perform(actionOnItemAtPosition<MainAdapter.MainHolder>(5,click()))
                onView(withId(R.id.main_recyclerview)).perform(actionOnItemAtPosition<MainAdapter.MainHolder>(6,click()))
                onView(withId(R.id.main_recyclerview)).perform(actionOnItemAtPosition<MainAdapter.MainHolder>(7,click()))
                onView(withId(R.id.main_recyclerview)).perform(actionOnItemAtPosition<MainAdapter.MainHolder>(8,click()))
                onView(withId(R.id.main_recyclerview)).perform(actionOnItemAtPosition<MainAdapter.MainHolder>(9,click()))
            }
        }
    }

    @Test
    fun test_recyclerview_on_scroll(){
        if(isConnected){
            runBlocking {
                delay(2000L)
                onView(withId(R.id.main_recyclerview)).perform(swipeUp())
                onView(withId(R.id.main_recyclerview)).perform(swipeUp())
                onView(withId(R.id.main_recyclerview)).perform(swipeUp())
                onView(withId(R.id.main_recyclerview)).perform(swipeUp())
                onView(withId(R.id.main_recyclerview)).perform(swipeUp())

                delay(500L)
                onView(withId(R.id.main_recyclerview)).perform(swipeDown())
                onView(withId(R.id.main_recyclerview)).perform(swipeDown())
            }
        }
    }
}