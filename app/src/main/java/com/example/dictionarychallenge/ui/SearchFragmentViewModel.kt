package com.example.dictionarychallenge.ui


import androidx.lifecycle.*
import com.example.dictionarychallenge.DictionaryRepository
import com.example.dictionarychallenge.data.SearchedWordResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class SearchFragmentViewModel internal constructor(
    private val dictionaryRepository: DictionaryRepository
) : ViewModel() {

    /** Show a loading spinner if true*/
    private val _spinner = MutableLiveData(false)
    val spinner: LiveData<Boolean> get() = _spinner

    /**take the data into the result live data and forward it to next screen*/

    var wordResult: SearchedWordResponse? = null

    /** Request a snackBar to display a string or error when API call failed.
     * The private variable will achieve encapsulation because we don't want to expose
     * MutableLiveData out of this class.*/
    private val _snackBar = MutableLiveData<String?>()
    val snackBar: LiveData<String?> get() = _snackBar


    fun makeAPICallWithSuspendFunction(term: CharSequence) {
        viewModelScope.launch {
            _spinner.value = true
            val result = dictionaryRepository.fetchRecentSearchedWord(term)
            _spinner.value = false
            if (result.isSuccessful) {
                wordResult = result.body()
            } else {
                //we can implement simple class to throw error but as of now displaying errror to user is good suggetion
                _snackBar.value = result.errorBody().toString()
            }
        }
    }
}

