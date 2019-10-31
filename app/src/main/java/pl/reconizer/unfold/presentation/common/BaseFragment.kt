package pl.reconizer.unfold.presentation.common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestack.StateChange
import pl.reconizer.unfold.MainActivity
import pl.reconizer.unfold.OnBackPressedListener
import pl.reconizer.unfold.R
import pl.reconizer.unfold.domain.entities.errors.ErrorsContainer
import pl.reconizer.unfold.presentation.customviews.dialogs.ErrorDialogBuilder
import pl.reconizer.unfold.presentation.customviews.dialogs.LoadingDialog
import pl.reconizer.unfold.presentation.customviews.dialogs.PrettyDialog
import pl.reconizer.unfold.presentation.mvp.IView
import pl.reconizer.unfold.presentation.navigation.keys.AuthenticationStartKey

open class BaseFragment : Fragment(), IView, OnBackPressedListener {

    private var errorDialog: PrettyDialog? = null

    private lateinit var loaderDialog: LoadingDialog

    val navigator: Backstack
        get() = (activity as MainActivity).navigator

    open val softInputMode: Int by lazy { (activity as MainActivity).defaultSoftInputMode }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.window?.setSoftInputMode(softInputMode)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loaderDialog = LoadingDialog(requireContext())

        if (withStatusBar()) {
            (activity as MainActivity).showStatusBar()
        } else {
            (activity as MainActivity).hideStatusBar()
        }
    }

    override fun onPause() {
        super.onPause()
        loaderDialog.hideDialog()
    }

    override fun showGenericError() {
        context?.let {
            showErrorMessage(it.resources.getString(R.string.error_something_went_wrong))
        }
    }

    override fun showConnectionError() {
        showGenericError()
    }

    override fun showAuthorizationError() {
        navigator.setHistory(
                mutableListOf(AuthenticationStartKey()),
                StateChange.REPLACE
        )
    }

    override fun showParametrizedError(errors: ErrorsContainer, translatedErrorMessage: String?) {
        if (translatedErrorMessage == null) {
            showGenericError()
        } else {
            showErrorMessage(translatedErrorMessage)
        }
    }

    override fun showServerError() {
        showGenericError()
    }

    open fun withStatusBar(): Boolean {
        return false
    }

    override fun goBack(): Boolean {
        navigator.goBack()
        return true
    }

    override fun showLoader() {
        if (!loaderDialog.isShowing()) {
            loaderDialog.showDialog()
        }
    }

    override fun hideLoader() {
        loaderDialog.hideDialog()
    }

    protected fun showErrorMessage(msg: String) {
        context?.let {
            errorDialog?.dismiss()
            errorDialog = ErrorDialogBuilder(it)
                .setErrorMessage(msg)
                .build()
            errorDialog?.show(childFragmentManager, "error")
        }
    }
}