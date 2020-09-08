package com.gkgio.borsch_cooker.profile.address

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.BaseFragment
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.closeKeyboard
import com.gkgio.borsch_cooker.ext.createViewModel
import com.gkgio.borsch_cooker.ext.observeValue
import com.gkgio.borsch_cooker.ext.openKeyBoard
import kotlinx.android.synthetic.main.fragment_find_address.*

class FindAddressFragment : BaseFragment<FindAddressViewModel>() {

    private var findAddressRecyclerAdapter: FindAddressRecyclerAdapter? = null

    override fun getLayoutId(): Int = R.layout.fragment_find_address

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.findAddressViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAddressesRv()

        addressSearchView.setSearchViewChangeListener {
            viewModel.onSearchTextChanged(it)
        }

        addressSearchView.setLeftIconClickListener {
            viewModel.onBackClick()
        }

        addressSearchView.requestFocusForInput()
        openKeyBoard()

        viewModel.closeKeyBoard.observeValue(this) {
            closeKeyboard()
        }

        viewModel.state.observeValue(this) { state ->
            progress.isVisible = state.isProgress
            state.geoSuggestionList?.let {
                findAddressRecyclerAdapter?.setGeoSuggestionList(it)
            }
        }
    }

    private fun initAddressesRv() {
        findAddressRecyclerAdapter = FindAddressRecyclerAdapter {
            if (it.data.streetWithType == null || it.data.house == null) {
                addressSearchView.currentInput = it.value
                openKeyBoard()
            } else {
                viewModel.onAddressSelectClick(it)
            }
        }
        rvAddresses.layoutManager = LinearLayoutManager(context)
        rvAddresses.adapter = findAddressRecyclerAdapter
    }

    override fun onStop() {
        addressSearchView.clearFocusForInput()
        closeKeyboard(addressSearchView)
        super.onStop()
    }
}