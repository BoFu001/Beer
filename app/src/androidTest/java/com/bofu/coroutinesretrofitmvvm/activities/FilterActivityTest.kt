package com.bofu.coroutinesretrofitmvvm.activities

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.bofu.coroutinesretrofitmvvm.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class FilterActivityTest{

    @get: Rule
    val filterActivityRole = ActivityScenarioRule(FilterActivity::class.java)

    @Test
    fun test_activity_in_view(){
        onView(withId(R.id.activity_filter)).check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun test_ui_elements_in_view(){
        onView(withId(R.id.filter_textView)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.filter_year_range)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.filter_button)).check(ViewAssertions.matches(isDisplayed()))
        onView(withId(R.id.clean_button)).check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun test_is_text_displyed_on_button(){
        onView(withId(R.id.filter_button)).check(ViewAssertions.matches(withText(R.string.filter_btn)))
        onView(withId(R.id.clean_button)).check(ViewAssertions.matches(withText(R.string.clean_btn)))
    }

    @Test
    fun test_on_RangeSlider_swipe(){
        onView(withId(R.id.filter_year_range)).perform(swipeRight())
        onView(withId(R.id.filter_year_range)).perform(swipeLeft())
        onView(withId(R.id.filter_year_range)).perform(swipeRight())
        onView(withId(R.id.filter_year_range)).perform(swipeLeft())
    }

}