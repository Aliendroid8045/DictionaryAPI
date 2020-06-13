package com.example.dictionarychallenge.ui

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.dictionarychallenge.R
import com.example.dictionarychallenge.data.SearchedWordResponse
import com.example.dictionarychallenge.utilities.VoteFilter
import com.example.dictionarychallenge.utilities.getViewModelFactory
import kotlinx.android.synthetic.main.word_list_frag.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class WordListFragment : Fragment() {

    private lateinit var wordListResponse: SearchedWordResponse

    val args: WordListFragmentArgs by navArgs()

    private val viewModel by viewModels<WordListFragmentViewModel> { getViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        wordListResponse = args.wordlistresponse
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.word_list_frag, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.setWordResponse(wordListResponse)
        }
        setUpRecyclerViewAndObserveDataChange()
    }


    private fun setUpRecyclerViewAndObserveDataChange() {
        viewModel.wordResultList.observe(viewLifecycleOwner, Observer {
            word_list.also {
                it.layoutManager = LinearLayoutManager(requireContext())
                it.setHasFixedSize(true)
                it.adapter =
                    viewModel.wordResultList.value?.toList()?.let { it1 -> WordResultAdapter(it1) }
            }
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