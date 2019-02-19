package pl.reconizer.unfold.presentation.common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.zhuinden.simplestack.Backstack
import com.zhuinden.simplestack.StateChange
import pl.reconizer.unfold.MainActivity
import pl.reconizer.unfold.OnBackPressedListener
import pl.reconizer.unfold.data.entities.Error
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
            errorDialog?.dismiss()
            errorDialog = ErrorDialogBuilder(it).build()
            errorDialog?.show(childFragmentManager, "error")
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

    override fun showParametrizedError(errorEntity: Error) {
        showGenericError()
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
}