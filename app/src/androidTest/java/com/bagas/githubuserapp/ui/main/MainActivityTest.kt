package com.bagas.githubuserapp.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.bagas.githubuserapp.utils.EspressoIdlingResource
import com.bagas.githubuserapp.R
import com.bagas.githubuserapp.ui.detail.DetailUserActivity
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun loadUserList_Success() {
        onView(withId(R.id.rv_users)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_users)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
    }

    @Test
    fun loadDetailUser_Success() {
        Intents.init()
        onView(withId(R.id.rv_users)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        intended(hasComponent(DetailUserActivity::class.java.name))
        onView(withId(R.id.tv_avatar)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_username)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_name)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_bio)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_repos)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_repos)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        pressBack()
    }

    @Test
    fun loadSearchedUser_Success() {
        Intents.init()
        onView(withId(R.id.searchBar)).check(matches(isDisplayed()))
        onView(withId(R.id.searchBar)).perform(typeText("bagaspardanailham18"))
        onView(withId(R.id.rv_searched_users)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_searched_users)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(R.id.rv_searched_users)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))
        intended(hasComponent(DetailUserActivity::class.java.name))
        pressBack()
    }

}