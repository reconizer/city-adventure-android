package pl.reconizer.cityadventure.presentation.common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.zhuinden.simplestack.Backstack
import pl.reconizer.cityadventure.MainActivity
import pl.reconizer.cityadventure.OnBackPressedListener
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.presentation.authentication.login.LoginFragment
import pl.reconizer.cityadventure.presentation.customviews.dialogs.ErrorDialogBuilder
import pl.reconizer.cityadventure.presentation.customviews.dialogs.PrettyDialog
import pl.reconizer.cityadventure.presentation.mvp.IView
import pl.reconizer.cityadventure.presentation.navigation.INavigator

open class BaseFragment : Fragment(), IView, OnBackPressedListener {

    private var errorDialog: PrettyDialog? = null

    val navigator: Backstack
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
            errorDialog = ErrorDialogBuilder(it).build()
            errorDialog?.show(childFragmentManager, "error")
        }
    }

    override fun showConnectionError() {
        showGenericError()
    }

    override fun showAuthorizationError() {
        //navigator.goTo(LoginFragment.newInstance())
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
}