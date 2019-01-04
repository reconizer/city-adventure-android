package pl.reconizer.cityadventure.presentation.customviews

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isGone
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog.view.*
import pl.reconizer.cityadventure.R

class PrettyDialog : DialogFragment() {

    var headerText: String? = null

    @DrawableRes
    var contentIcon: Int? = null

    var contentText: String? = null

    var firstButtonText: String? = null
    var secondButtonText: String? = null

    var firstButtonClickListener: (() -> Unit)? = null
    var secondButtonClickListener: (() -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog, null, true)
        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomDialog).run {
            setView(dialogView)
            create()
        }
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

}