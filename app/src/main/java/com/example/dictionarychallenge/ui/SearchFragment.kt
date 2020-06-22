package com.example.dictionarychallenge.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.dictionarychallenge.R
import com.example.dictionarychallenge.ui.viewmodel.SearchFragmentViewModel
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


    fun isNetworkConnected(): Boolean {
        val cm = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true

    }

    private fun handleButtonClick(buttonSearch: Button) {
        buttonSearch.setOnClickListener {
            hideKeyboard(btn_search)
            if (!isNetworkConnected()) {
                displayNetworkErrorAlert()
                return@setOnClickListener
            }
            viewModel.makeAPICallWithSuspendFunction(search_bar.text)
        }
    }

    private fun displayNetworkErrorAlert() {
        val dialogBuilder = AlertDialog.Builder(this.requireContext())
        dialogBuilder.setMessage(getString(R.string.please_connect_network))
            .setPositiveButton(getString(R.string.ok)) { dialog, id ->
                dialog.dismiss()
            }
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle(getString(R.string.network_error))
        alert.show()
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
            if (value == true) {
                navigateUserToResultFragment()
            }
        })
    }

    private fun navigateUserToResultFragment() {
        val directions = SearchFragmentDirections.actionSearchFragmentToWordListFragment()
        findNavController().navigate(directions)
    }
}