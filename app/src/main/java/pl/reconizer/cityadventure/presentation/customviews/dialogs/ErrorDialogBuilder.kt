package pl.reconizer.cityadventure.presentation.customviews.dialogs

import android.content.Context
import pl.reconizer.cityadventure.R

class ErrorDialogBuilder(private val context: Context) {

    var dismissListener: (() -> Unit)? = null

    private val prettyDialog = PrettyDialog().apply {
        contentIcon = R.drawable.dialog_error
        contentText = this@ErrorDialogBuilder.context.resources.getString(R.string.error_something_went_wrong)
        firstButtonText = this@ErrorDialogBuilder.context.resources.getString(R.string.common_ok)
        secondButtonText = null
        headerText = null
        firstButtonClickListener = {
            dismiss()
            dismissListener?.invoke()
        }
        secondButtonClickListener = null
    }

    fun setErrorMessage(msg: String): ErrorDialogBuilder {
        prettyDialog.contentText = msg
        return this
    }

    fun setButtonText(text: String): ErrorDialogBuilder {
        prettyDialog.firstButtonText = text
        return this
    }

    fun setOnDismissListener(callback: (() -> Unit)): ErrorDialogBuilder {
        dismissListener = callback
        return this
    }

    fun build(): PrettyDialog {
        return prettyDialog
    }

}