package pl.reconizer.cityadventure.presentation.common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import pl.reconizer.cityadventure.MainActivity
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.presentation.authentication.login.LoginFragment
import pl.reconizer.cityadventure.presentation.customviews.PrettyDialog
import pl.reconizer.cityadventure.presentation.mvp.IView
import pl.reconizer.cityadventure.presentation.navigation.INavigator

open class BaseFragment : Fragment(), IView {

    private var errorDialog: PrettyDialog? = null

    val navigator: INavigator
        get() = (activity as MainActivity).navigator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (withStatusBar()) {
            (activity as MainActivity).showStatusBar()
        } else {
            (activity as MainActivity).hideStatusBar()
        }
    }

    override fun showGenericError() {
        context?.let {
            errorDialog?.dismiss()
            errorDialog = PrettyDialog.ErrorDialogBuilder(it).build()
            errorDialog?.show(childFragmentManager, "error")
        }
    }

    override fun showConnectionError() {
        showGenericError()
    }

    override fun showAuthorizationError() {
        navigator.goTo(LoginFragment.newInstance())
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
}