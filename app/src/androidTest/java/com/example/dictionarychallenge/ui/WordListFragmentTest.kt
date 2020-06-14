package com.example.dictionarychallenge.ui

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.dictionarychallenge.R
import com.example.dictionarychallenge.data.Description
import com.example.dictionarychallenge.data.SearchedWordResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
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
        val wordResponse = SearchedWordResponse(arrayListOf(description, description2))

        //when
        val bundle = WordListFragmentArgs(wordResponse).toBundle()
        launchFragmentInContainer<WordListFragment>(bundle, R.style.AppTheme)

        //then
        onView(withId(R.id.word_list)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

}