package pl.reconizer.unfold.presentation.customviews.dialogs

import android.content.Context
import pl.reconizer.unfold.R

class LocationInfoDialogBuilder(private val context: Context, private val dialogType: Type) {

    var actionListener: (() -> Unit)? = null

    private val prettyDialog = PrettyDialog().apply {
        isCancelable = false
        contentIcon = R.drawable.dialog_location
        secondButtonText = null
        headerText = null
        firstButtonClickListener = {
            dismiss()
            actionListener?.invoke()
        }
        secondButtonClickListener = null

        when(dialogType) {
            Type.EXPLANATION -> {
                contentText = this@LocationInfoDialogBuilder.context.resources.getString(R.string.location_access_explanation)
                firstButtonText = this@LocationInfoDialogBuilder.context.resources.getString(R.string.common_ok)
            }
            Type.ACCESS_DENIED -> {
                contentText = this@LocationInfoDialogBuilder.context.resources.getString(R.string.location_access_denied)
                firstButtonText = this@LocationInfoDialogBuilder.context.resources.getString(R.string.common_settings)
            }
            Type.UNABLE_TO_ACCESS -> {
                contentText = this@LocationInfoDialogBuilder.context.resources.getString(R.string.location_error_occured)
                firstButtonText = this@LocationInfoDialogBuilder.context.resources.getString(R.string.common_settings)
            }
        }
    }

    fun build(): PrettyDialog {
        return prettyDialog
    }

    enum class Type {
        EXPLANATION,
        ACCESS_DENIED,
        UNABLE_TO_ACCESS
    }

}