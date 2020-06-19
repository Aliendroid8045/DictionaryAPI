package com.example.dictionarychallenge.ui


import androidx.lifecycle.*
import com.example.dictionarychallenge.DictionaryRepository
import com.example.dictionarychallenge.data.Description
import com.example.dictionarychallenge.utilities.VoteFilter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class SearchFragmentViewModel internal constructor(
    private val dictionaryRepository: DictionaryRepository
) : ViewModel() {

    /** Show a loading spinner if true*/
    private val _spinner = MutableLiveData(true)
    val spinner: LiveData<Boolean> get() = _spinner

    /**take the data into the result live data and forward it to next screen*/

    private val _wordResponseList = MutableLiveData<List<Description>>()
    val wordResponseList: LiveData<List<Description>> = _wordResponseList

    fun makeAPICallWithSuspendFunction(term: CharSequence) {
        viewModelScope.launch {
            val result = dictionaryRepository.fetchRecentSearchedWord(term)
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
        var filteredList: List<Description>? = null

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
