package pl.reconizer.cityadventure.presentation.customviews

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isGone
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog.view.*
import pl.reconizer.cityadventure.R
import java.lang.ref.WeakReference

class PrettyDialog : DialogFragment() {

    var headerText: String? = null

    @DrawableRes
    var contentIcon: Int? = null

    var contentText: String? = null
        set(value) {
            field = value
            contentTextView?.get()?.text = value
        }

    var firstButtonText: String? = null
    var secondButtonText: String? = null

    var firstButtonClickListener: (() -> Unit)? = null
    var secondButtonClickListener: (() -> Unit)? = null

    private var contentTextView: WeakReference<TextView>? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog, null, true)
        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomDialog).run {
            setView(dialogView)
            create()
        }
        contentTextView = WeakReference(dialogView.dialogText)
        buildDialog(dialogView)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.decorView?.setBackgroundResource(android.R.color.transparent);
        return dialog
    }

    private fun buildDialog(view: View) {
        view.dialogHeader.closeButtonClickListener = { dismiss() }
        view.dialogHeader.apply {
            isGone = headerText.isNullOrBlank()
            text = headerText
        }

        view.dialogIcon.apply {
            isGone = contentIcon == null
            contentIcon?.let { setImageResource(it) }
        }

        view.dialogText.text = contentText

        view.dialogButtonFirst.apply {
            text = firstButtonText
            setOnClickListener { firstButtonClickListener?.invoke() }
        }

        view.dialogButtonSecond.apply {
            isGone = secondButtonText.isNullOrBlank()
            text = secondButtonText
            setOnClickListener { secondButtonClickListener?.invoke() }

        }
    }

    class ErrorDialogBuilder(private val context: Context) {

        private var dismissListener: (() -> Unit)? = null

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

}