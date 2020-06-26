package com.example.dictionarychallenge.ui.viewmodel


import android.util.Log
import androidx.lifecycle.*
import com.example.dictionarychallenge.DictionaryRepository
import com.example.dictionarychallenge.data.Description
import com.example.dictionarychallenge.utilities.VoteFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SearchFragmentViewModel internal constructor(
    private val dictionaryRepository: DictionaryRepository
) : ViewModel() {

    /** Show a loading spinner if true*/
    private val _spinner = MutableLiveData(false)
    val spinner: LiveData<Boolean> get() = _spinner

    /**take the data into the result live data and forward it to next screen*/

    private val _wordResponseList = MutableLiveData<List<Description>>()
    val wordResponseList: LiveData<List<Description>> = _wordResponseList

    fun makeAPICallWithSuspendFunction(term: CharSequence) {
        viewModelScope.launch(Dispatchers.Main) {
            _spinner.value = true
            val deferred =
                viewModelScope.async { dictionaryRepository.fetchRecentSearchedWord(term) }
            val result = deferred.await()
            _wordResponseList.value = result
            _spinner.value = false
        }
    }

    fun setFilteringType(requestType: VoteFilter) {

        when (requestType) {
            VoteFilter.MOST_VOTED -> filterItems(true)

            VoteFilter.LESS_VOTED -> filterItems(false)
        }
    }

    private fun filterItems(mostVoted: Boolean) {
        val filteredList: List<Description>?

        when (mostVoted) {
            true -> {
                filteredList =
                    _wordResponseList.value?.sortedWith(compareByDescending { it.thumbs_up.toInt() })
            }
            else -> filteredList =
                _wordResponseList.value?.sortedWith(compareBy { it.thumbs_up.toInt() })
        }
        _wordResponseList.value = filteredList
    }
}
