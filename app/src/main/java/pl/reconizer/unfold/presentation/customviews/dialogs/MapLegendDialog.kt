package pl.reconizer.unfold.presentation.customviews.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_with_header_and_content.view.*
import pl.reconizer.unfold.R

class MapLegendDialog : DialogFragment() {

    @LayoutRes
    var contentLayoutResId: Int? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_with_header_and_content, null, true)
        val contentContainer = dialogView.contentContainer
        val contentView = LayoutInflater.from(context).inflate(contentLayoutResId!!, contentContainer, true)
        dialogView.dialogHeader.text = resources.getString(R.string.map_legend_title)
        dialogView.dialogHeader.closeButtonClickListener = { dismiss() }
        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomDialog).run {
            setView(dialogView)
            create()
        }
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.decorView?.setBackgroundResource(android.R.color.transparent)
        return dialog
    }

}