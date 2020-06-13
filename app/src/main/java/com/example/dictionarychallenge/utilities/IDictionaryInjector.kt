package com.example.dictionarychallenge.utilities

import android.content.Context
import com.example.dictionarychallenge.DictionaryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
interface IDictionaryInjector {

    fun provideDictionaryRepository(context: Context): DictionaryRepository
}


val Injector: IDictionaryInjector get() = currentInjector

@Volatile
private var currentInjector: IDictionaryInjector = getDictionaryReady

@OptIn(ExperimentalCoroutinesApi::class)
private object getDictionaryReady : IDictionaryInjector {

    private fun service() = NetworkService()

    private fun getDictionaryRepository(): DictionaryRepository {
        return DictionaryRepository.getInstance(
            service()
        )
    }

    override fun provideDictionaryRepository(context: Context): DictionaryRepository {
        return getDictionaryRepository()
    }
}

