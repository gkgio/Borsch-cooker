package com.gkgio.borsch_cooker.profile.name

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.BaseFragment
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.createViewModel
import com.gkgio.borsch_cooker.ext.notIsNullOrBlank
import com.gkgio.borsch_cooker.ext.observeValue
import com.gkgio.borsch_cooker.ext.setDebounceOnClickListener
import kotlinx.android.synthetic.main.fragment_input_name.*

class InputNameFragment : BaseFragment<InputNameViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_input_name

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.inputNameViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.progressState.observeValue(viewLifecycleOwner) {
            progress.isVisible = it
        }

        iconBack.setDebounceOnClickListener {
            viewModel.onBackClick()
        }

        saveBtn.setDebounceOnClickListener {
            if (nameEditText.text.notIsNullOrBlank() && secondNameEditText.text.notIsNullOrBlank()) {
                viewModel.onSaveNameClicked(
                    nameEditText.text.toString(),
                    secondNameEditText.text.toString()
                )
            }
        }
    }
}