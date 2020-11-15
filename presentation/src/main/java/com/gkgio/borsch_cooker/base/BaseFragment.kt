package com.gkgio.borsch_cooker.base

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.nonNullObserve
import com.gkgio.borsch_cooker.utils.DialogUtils
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    protected lateinit var viewModel: VM
    protected val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("UNCHECKED_CAST")
        AppInjector.appComponent.inject(this as BaseFragment<BaseViewModel>)

        viewModel = provideViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutId(), container, false)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            onBackClick()
        }
        return view
    }

    protected fun showDialog(dialog: DialogFragment, tag: String) {
        if (isDetached || childFragmentManager.findFragmentByTag(tag)?.isAdded == true) {
            return
        }

        dialog.show(childFragmentManager, tag)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeErrorEvents()
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun provideViewModel(): VM

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    private fun subscribeErrorEvents() {
        viewModel.errorEvent.nonNullObserve(this) {
            showError(it ?: getString(R.string.default_error))
        }

        viewModel.noInternetErrorEvent.nonNullObserve(this) {
            showNetworkError(it ?: getString(R.string.default_error))
        }

        viewModel.unsupportedVersionErrorEvent.nonNullObserve(this) {
            showUnsupportedVersionError(it)
        }

        viewModel.googleGmsApiNotConnectErrorEvent.nonNullObserve(this) {
            showError(getString(R.string.api_not_connect_error))
        }
    }

    protected open fun showError(message: String) {
        DialogUtils.showError(
            view,
            message
        )
    }

    protected open fun showNetworkError(message: String) {
        DialogUtils.showError(
            view,
            message
        )
    }

    protected fun showUnsupportedVersionError(message: String) {
        DialogUtils.showError(
            view,
            message,
            getString(R.string.title_redirect),
            View.OnClickListener {
                activity?.packageName?.let { packageName ->
                    viewModel.onMarketClick(packageName)
                }
            }
        )
    }

    protected fun showDialogWindow(
        title: String,
        body: String,
        button: String = getString(R.string.dialog_success_button)
    ) {
        val builder = AlertDialog.Builder(requireActivity())
        builder
            .setTitle(title)
            .setMessage(body)
            .setPositiveButton(
                button
            ) { dialog, _ ->
                dialog.cancel()
            }
        builder.create()
        builder.show()
    }

    protected fun Disposable.addDisposable() {
        disposables.add(this)
    }

    open fun onBackClick() {
        viewModel.onBackClick()
    }
}