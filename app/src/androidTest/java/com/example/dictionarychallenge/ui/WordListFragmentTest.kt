package com.example.dictionarychallenge.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.dictionarychallenge.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class WordListFragmentTest {

    @Test
    fun launch_listFragment_when_resultSuccess() {

        //when
        launchFragmentInContainer<WordListFragment>()

        //then
        onView(withId(R.id.rv)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

}