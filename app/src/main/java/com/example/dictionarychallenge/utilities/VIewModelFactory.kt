package com.example.dictionarychallenge.utilities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dictionarychallenge.DictionaryRepository
import com.example.dictionarychallenge.ui.WordListFragmentViewModel
import com.example.dictionarychallenge.ui.SearchFragmentViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi


/**
 * Factory for all ViewModels.
 */
@Suppress("UNCHECKED_CAST")
@ExperimentalCoroutinesApi
class ViewModelFactory(private val repository: DictionaryRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(SearchFragmentViewModel::class.java) ->
                    SearchFragmentViewModel(
                        repository
                    )
                isAssignableFrom(WordListFragmentViewModel::class.java) ->
                    WordListFragmentViewModel(
                        repository
                    )
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
