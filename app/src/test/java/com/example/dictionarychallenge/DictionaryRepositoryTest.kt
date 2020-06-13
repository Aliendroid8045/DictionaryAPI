package com.example.dictionarychallenge

import com.example.dictionarychallenge.data.Description
import com.example.dictionarychallenge.data.SearchedWordResponse
import com.example.dictionarychallenge.utilities.NetworkService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.IsEqual
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DictionaryRepositoryTest {
    // we follow GIVEN,WHEN, THEN principle in testing

    private lateinit var dictionaryRepository: DictionaryRepository
    private lateinit var networkInjector: NetworkService


    @Before
    //given
    fun createRepository() {
        networkInjector = NetworkService()
        dictionaryRepository = DictionaryRepository(networkInjector)
    }

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

    private val wordResponse = SearchedWordResponse(arrayListOf(description, description2))


    //used runBlocking block because our fetchResponseWord function is suspend
    @Test
    fun getWordResponse_whenApiCall_happen() {
        //when
        val response = runBlocking {
            dictionaryRepository.fetchRecentSearchedWord("term")

            //then
            assertThat(wordResponse, IsEqual(wordResponse))
        }

    }
}