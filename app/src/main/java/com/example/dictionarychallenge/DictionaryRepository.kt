package com.example.dictionarychallenge

import com.example.dictionarychallenge.data.SearchedWordResponse
import com.example.dictionarychallenge.utilities.NetworkService
import kotlinx.coroutines.*
import retrofit2.Response

/**
 * Repository module for handling data operations.
 *
 * This class mainly developed to work with online offline data, this POC used
 * separation of concern principle to achieve separation of UI, DATA and BUSINESS LOGIC
 * The Repository only handle the data layer either each fetch from local stored data or from
 * network
 *
 * The @ExperimentalCoroutinesApi and indicate that experimental APIs are being used.
 */

@ExperimentalCoroutinesApi
class DictionaryRepository internal constructor(private val networkService: NetworkService) {


    companion object {
        @Volatile
        private var dictionaryRepoInstance: DictionaryRepository? = null

        fun getInstance(dictionaryService: NetworkService) =
            dictionaryRepoInstance ?: synchronized(this) {
                dictionaryRepoInstance
                    ?: DictionaryRepository(dictionaryService).also { dictionaryRepoInstance = it }
            }
    }

    /**
     * Fetch a new searched word from the network
     */
    suspend fun fetchRecentSearchedWord(term: CharSequence) : Response<SearchedWordResponse> {
       return networkService.getResultFromNetwork(term)
        //we can implement database @Room when call failed or error happen, we can pull data from here
    }
}