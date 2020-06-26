package com.example.dictionarychallenge

import android.app.Application
import com.example.dictionarychallenge.utilities.Injector


class DictionaryApplication : Application() {

    val dictionaryRepository: DictionaryRepository get() = Injector.provideDictionaryRepository(this)

}
