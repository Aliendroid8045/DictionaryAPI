package com.example.dictionarychallenge.utilities

import androidx.fragment.app.Fragment
import com.example.dictionarychallenge.DictionaryApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
fun Fragment.getViewModelFactory(): ViewModelFactory {
    val repository =
        (requireContext().applicationContext as DictionaryApplication).dictionaryRepository
    return ViewModelFactory(repository)
}
