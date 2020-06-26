package com.example.dictionarychallenge.ui

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.dictionarychallenge.R
import com.example.dictionarychallenge.ui.viewmodel.SearchFragmentViewModel
import com.example.dictionarychallenge.utilities.VoteFilter
import com.example.dictionarychallenge.utilities.getViewModelFactory


class WordListFragment : Fragment() {


    private val viewModel by activityViewModels<SearchFragmentViewModel> { getViewModelFactory() }
    val adapter = WordResultAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.word_list_frag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rv = view.findViewById<RecyclerView>(R.id.rv)
        rv.layoutManager = LinearLayoutManager(activity)
        rv.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        rv.adapter = adapter
        viewModel.wordResponseList.observe(requireActivity(), Observer {
            adapter.submitList(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.filter_task_menu_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_filter) {
            showFilteringPopUpMenu()
            return true
        }
        return false
    }

    private fun showFilteringPopUpMenu() {
        val view = activity?.findViewById<View>(R.id.menu_filter) ?: return
        PopupMenu(requireContext(), view).run {
            menuInflater.inflate(R.menu.filter_menu, menu)
            setOnMenuItemClickListener {
                viewModel.setFilteringType(
                    when (it.itemId) {
                        R.id.low -> VoteFilter.LESS_VOTED
                        R.id.high -> VoteFilter.MOST_VOTED
                        else -> VoteFilter.NO_VOTE_PREFERENCE
                    }
                )
                true
            }
            show()
        }
    }
}