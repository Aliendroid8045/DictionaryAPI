package com.example.dictionarychallenge


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.dictionarychallenge.ui.viewmodel.SearchFragmentViewModel
import com.example.dictionarychallenge.data.Description
import com.nhaarman.mockitokotlin2.doReturn
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever

// we follow GIVEN,WHEN,THEN principle in testing
class SearchFragmentViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcher()

    //GIVEN
    @get:Rule
    private val dictionaryRepository: DictionaryRepository = mock()
    private lateinit var underTest: SearchFragmentViewModel
    private val testModel = Description(
        "term", "term", "term",
        "term", "term", "term", "term", "term"
    )
    @Before
    fun setUp() {
        runBlocking {
            whenever(dictionaryRepository.fetchRecentSearchedWord("term")).doReturn(
                mutableListOf(
                    testModel
                )
            )
        }
        underTest = SearchFragmentViewModel(dictionaryRepository)
    }

    //used runBlocking block because our fetchResponseWord function is suspend function
    @Test
    fun getWordResponse_whenApiCall_happen() {

      //when
        underTest.makeAPICallWithSuspendFunction(testModel.word)
        mainDispatcherRule.dispatcher.runCurrent()

        //then
        runBlocking {
            verify(dictionaryRepository).fetchRecentSearchedWord(testModel.word)
        }
    }
}
