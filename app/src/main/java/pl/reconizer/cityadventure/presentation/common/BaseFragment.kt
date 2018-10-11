package pl.reconizer.cityadventure.presentation.common

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import pl.reconizer.cityadventure.R
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.presentation.mvp.IView

open class BaseFragment : Fragment(), IView {

    private var genericErrorDialog: AlertDialog? = null

    override fun showGenericError() {
        context?.let {
            if (genericErrorDialog == null) {
                genericErrorDialog = AlertDialog.Builder(it).run {
                    setMessage(R.string.error_something_went_wrong)
                    setNeutralButton(R.string.close) { dialog, _ -> dialog.dismiss() }
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showParametrizedError(errorEntity: Error) {
        showGenericError()
    }

    override fun showServerError() {
        showGenericError()
    }
}