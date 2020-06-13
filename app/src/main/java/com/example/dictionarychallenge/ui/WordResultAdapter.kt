package com.example.dictionarychallenge.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionarychallenge.R
import com.example.dictionarychallenge.data.Description
import com.example.dictionarychallenge.databinding.ListItemBindingImpl

class WordResultAdapter(
    private var wordList: List<Description>

) : RecyclerView.Adapter<WordResultAdapter.WordListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordListHolder =
        WordListHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item,
                parent,
                false
            )
        )

    override fun getItemCount() = wordList.size

    override fun onBindViewHolder(holder: WordListHolder, position: Int) {
        holder.listItemBindingImpl.descriptonData = wordList[position]
    }

    //we pass our data-binding root layout to this extended class RecyclerView.ViewHolder
    inner class WordListHolder(val listItemBindingImpl: ListItemBindingImpl) :
        RecyclerView.ViewHolder(listItemBindingImpl.root)
}

