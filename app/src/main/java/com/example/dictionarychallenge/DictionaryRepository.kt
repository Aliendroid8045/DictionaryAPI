package com.example.dictionarychallenge

import android.util.Log
import com.example.dictionarychallenge.data.Description
import com.example.dictionarychallenge.utilities.NetworkService
import com.example.dictionarychallenge.utilities.Result
import kotlinx.coroutines.*
import retrofit2.Response
import java.io.IOException

/**
 * Repository module for handling data operations.
 *
 * This class mainly developed to work with online offline data, this POC used
 * separation of concern principle to achieve separation of UI, DATA and BUSINESS LOGIC
 * The Repository only handle the data layer either each fetch from local stored data or from
 * network
 *
 */

class DictionaryRepository internal constructor(private val networkService: NetworkService) :
    BaseRepository() {


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
    suspend fun fetchRecentSearchedWord(term: CharSequence): MutableList<Description>? {
        return safeApiCall(
            call = { withContext(Dispatchers.IO) { networkService.getResultFromNetwork(term) } },
            error = "Error fetching data"
        )?.list?.toMutableList()

        //we can implement database @Room when call failed or error happen, we can pull data from here
    }

}

open class BaseRepository {
    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, error: String): T? {
        val result = newsApiOutput(call, error)
        var output: T? = null
        when (result) {
            is Result.Success ->
                output = result.output
            is Result.Error -> Log.e("Error", "The $error and the ${result.exception}")
        }
        return output

    }

    private suspend fun <T : Any> newsApiOutput(
        call: suspend () -> Response<T>,
        error: String
    ): Result<T> {
        val response = call.invoke()
        return if (response.isSuccessful)
            Result.Success(response.body()!!)
        else
            Result.Error(IOException("OOps .. Something went wrong due to  $error"))
    }
}