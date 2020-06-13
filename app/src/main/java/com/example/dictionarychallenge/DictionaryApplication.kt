package com.example.dictionarychallenge

import android.app.Application
import com.example.dictionarychallenge.utilities.Injector
import kotlinx.coroutines.ExperimentalCoroutinesApi

class DictionaryApplication : Application() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val dictionaryRepository: DictionaryRepository get() = Injector.provideDictionaryRepository(this)

}
