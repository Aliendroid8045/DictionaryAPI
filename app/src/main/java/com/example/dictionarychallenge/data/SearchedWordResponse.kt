package com.example.dictionarychallenge.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue


@Parcelize
data class SearchedWordResponse(val list: @RawValue ArrayList<Description>) : Parcelable

@Parcelize
data class Description(
    val definition: String,
    val permalink: String,
    val thumbs_up: String,
    val author: String,
    val word: String,
    val written_on: String,
    val example: String,
    val thumbs_down: String
) : Parcelable

