package pl.reconizer.cityadventure.presentation.common

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import pl.reconizer.cityadventure.MainActivity
import pl.reconizer.cityadventure.R
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.presentation.authentication.login.LoginFragment
import pl.reconizer.cityadventure.presentation.mvp.IView
import pl.reconizer.cityadventure.presentation.navigation.INavigator

open class BaseFragment : Fragment(), IView {

    private var genericErrorDialog: AlertDialog? = null

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
            if (genericErrorDialog == null) {
                genericErrorDialog = AlertDialog.Builder(it).run {
                    setMessage(R.string.error_something_went_wrong)
                    setNeutralButton(R.string.common_close) { dialog, _ -> dialog.dismiss() }
                    create()
                }
            }
            if (!genericErrorDialog!!.isShowing) { genericErrorDialog!!.show() }
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