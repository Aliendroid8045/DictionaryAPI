package com.example.dictionarychallenge.utilities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dictionarychallenge.DictionaryRepository
import com.example.dictionarychallenge.ui.viewmodel.SearchFragmentViewModel



/**
 * Factory for all ViewModels.
 */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: DictionaryRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(SearchFragmentViewModel::class.java) ->
                    SearchFragmentViewModel(
                        repository
                    )
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
