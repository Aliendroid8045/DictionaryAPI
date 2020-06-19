package com.example.dictionarychallenge.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.dictionarychallenge.R
import com.example.dictionarychallenge.utilities.getViewModelFactory
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class SearchFragment : Fragment() {

    private val viewModel by activityViewModels<SearchFragmentViewModel> { getViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        val searchBar = view.findViewById<EditText>(R.id.search_bar)
        val buttonSearch = view.findViewById<Button>(R.id.btn_search)
        handleKeyBoardEnterKey(searchBar)
        handleButtonClick(buttonSearch)
        handleSpinner()
        return view
    }

    private fun handleButtonClick(buttonSearch: Button) {
        buttonSearch.setOnClickListener {
            hideKeyboard(btn_search)
            viewModel.makeAPICallWithSuspendFunction(search_bar.text)
        }
    }

    fun handleKeyBoardEnterKey(searchBar: EditText) =
        searchBar.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                hideKeyboard(search_bar)
                viewModel.makeAPICallWithSuspendFunction(searchBar.text)
                true
            } else {
                false
            }
        }

    private fun hideKeyboard(searchView: View) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchView.windowToken, 0)
    }

    private fun handleSpinner() {
        // show the spinner when [MainViewModel.spinner] is true
        viewModel.spinner.observe(viewLifecycleOwner, Observer { value ->
            value.let { show -> this.spinner.visibility = if (show) View.VISIBLE else View.GONE }
            if (value == false) {
                navigateUserToResultFragment()
            }
        })
    }

    private fun navigateUserToResultFragment() {
        val directions =
            viewModel.wordResponseList.let {
                SearchFragmentDirections.actionSearchFragmentToResultFragment()
            }
        directions.let { findNavController().navigate(it) }
    }
}