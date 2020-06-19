package com.example.dictionarychallenge.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionarychallenge.R
import com.example.dictionarychallenge.data.Description
import com.example.dictionarychallenge.databinding.ListItemBindingImpl

class WordResultAdapter() : ListAdapter<Description, WordListHolder>(DescriptionRowDiffer) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordListHolder =
        WordListHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: WordListHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class WordListHolder(val listItemBindingImpl: ListItemBindingImpl) :
    RecyclerView.ViewHolder(listItemBindingImpl.root) {
    fun bind(description: Description) {
        listItemBindingImpl.descriptonData = description
        listItemBindingImpl.executePendingBindings()
    }
}

object DescriptionRowDiffer : DiffUtil.ItemCallback<Description>() {
    override fun areItemsTheSame(
        oldItem: Description,
        newItem: Description
    ): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(
        oldItem: Description,
        newItem: Description
    ): Boolean {
        return oldItem == newItem
    }
}
