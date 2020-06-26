package com.example.dictionarychallenge

import com.example.dictionarychallenge.data.Description
import com.example.dictionarychallenge.utilities.NetworkService
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.IsEqual
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test


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

    //used runBlocking block because our fetchResponseWord function is suspend
   /* @Test
    fun getWordResponse_whenApiCall_happen() = runBlocking {
        //when
        val response = dictionaryRepository.fetchRecentSearchedWord("term")
        val data: List<Description>? = response.

        //than
        assertThat(data?.get(0)?.word, IsEqual("Term"))

    }*/
}