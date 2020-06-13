package com.example.dictionarychallenge.ui

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dictionarychallenge.DictionaryRepository
import com.example.dictionarychallenge.R
import com.example.dictionarychallenge.data.Description
import com.example.dictionarychallenge.utilities.VoteFilter
import com.example.dictionarychallenge.data.SearchedWordResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class WordListFragmentViewModel(private val dictionaryRepository: DictionaryRepository) :
    ViewModel() {

    private val _wordResultList = MutableLiveData<List<Description>>()
    val wordResultList: LiveData<List<Description>> get() = _wordResultList

    private val _currentFilteringLabel = MutableLiveData<Int>()
    val currentFilteringLabel: LiveData<Int> get() = _currentFilteringLabel

    private var currentFiltering = VoteFilter.MOST_VOTED

    fun setWordResponse(response: SearchedWordResponse) {
        _wordResultList.value = response.list

    }

    fun setFilteringType(requestType: VoteFilter) {
        currentFiltering = requestType

        when (requestType) {
            VoteFilter.MOST_VOTED -> filterItems(R.string.most_voted, true)

            VoteFilter.LESS_VOTED -> filterItems(R.string.less_voted, false)
        }

    }

    private fun filterItems(@StringRes filteringLabelString: Int, mostVoted: Boolean) {
        _currentFilteringLabel.value = filteringLabelString
        val filteredList: List<Description>
        when (mostVoted) {
            true -> {
                filteredList =
                    _wordResultList.value?.sortedWith(compareByDescending { it.thumbs_up.toInt() })!!
            }
            else -> filteredList =
                _wordResultList.value?.sortedWith(compareBy { it.thumbs_up.toInt() })!!
        }
        _wordResultList.value = filteredList
    }

}