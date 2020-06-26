package com.example.dictionarychallenge.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.dictionarychallenge.R
import com.example.dictionarychallenge.data.Description
import com.example.dictionarychallenge.data.SearchedWordResponse
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class WordListFragmentTest {

    @Test
    fun launch_listFragment_when_resultSuccess() {

        //given
        val description = Description(
            "definition", "permalink",
            "thumbs_up",
            "author",
            "word",
            "defid",
            "current_vote",
            "written_on"
        )
        val description2 = Description(
            "definition", "permalink",
            "thumbs_up",
            "author",
            "word",
            "defid",
            "current_vote",
            "written_on"
        )
        val mockNavController = mock(NavController::class.java)
        val wordListFragment = launchFragmentInContainer<WordListFragment>()

        val wordResponse = SearchedWordResponse(arrayListOf(description, description2))
        //when

        //then
        onView(withId(R.id.rv)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

}