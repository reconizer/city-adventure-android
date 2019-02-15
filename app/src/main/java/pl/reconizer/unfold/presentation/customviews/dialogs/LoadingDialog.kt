package pl.reconizer.unfold.presentation.customviews.dialogs

import android.app.Dialog
import android.content.Context
import android.view.Window
import pl.reconizer.unfold.R


class LoadingDialog(private val context: Context) {

    private var dialog: Dialog? = null

    fun showDialog() {
        dialog = Dialog(context, R.style.FullScreenCustomDialog)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(false)
        dialog?.setContentView(R.layout.dialog_loader)

        dialog?.show()
    }

    fun hideDialog() {
        dialog?.dismiss()
    }

    fun isShowing(): Boolean {
        return dialog?.isShowing == true
    }

}
