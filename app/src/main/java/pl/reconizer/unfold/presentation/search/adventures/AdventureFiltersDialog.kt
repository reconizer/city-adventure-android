package pl.reconizer.unfold.presentation.search.adventures

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_adventure_filters.view.*
import kotlinx.android.synthetic.main.dialog_with_header_and_content.view.dialogHeader
import pl.reconizer.unfold.R

class AdventureFiltersDialog : DialogFragment() {

    var onApplyListener: ((filtersState: AdventureFilters) -> Unit)? = null

    lateinit var filtersState: AdventureFilters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        filtersState = arguments?.get(ADVENTURE_FILTERS_PARAM) as AdventureFilters? ?: throw IllegalArgumentException("AdventureFilters object is required")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_adventure_filters, null, true)
        dialogView.dialogHeader.closeButtonClickListener = { dismiss() }
        dialogView?.apply {
            rangeMinValue.text = "%d".format(filtersState.minRange.toInt())
            rangeMaxValue.text = "%d".format(filtersState.maxRange.toInt())
            range.progress = (filtersState.range * 100).toInt()
            rangeIndicator.text = formatRangeIndicator()
            difficultyLevelGroup.selection = filtersState.difficultyLevel

            range.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    filtersState = filtersState.copy(range = progress / 100f)
                    rangeIndicator.text = formatRangeIndicator()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}

            })

            difficultyLevelGroup.onSelectionChangedListener = { selectedDifficulty ->
                filtersState = filtersState.copy(difficultyLevel = selectedDifficulty)
            }

            applyButton.setOnClickListener {
                onApplyListener?.invoke(filtersState)
                this@AdventureFiltersDialog.dismiss()
            }

        }

        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomDialog).run {
            setView(dialogView)
            create()
        }
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.decorView?.setBackgroundResource(android.R.color.transparent)
        return dialog
    }

    private fun formatRangeIndicator(): String {
        return "${resources.getString(R.string.sort_range)} (${"%.2f".format((filtersState.maxRange - filtersState.minRange) * filtersState.range + filtersState.minRange)} km)"
    }

    companion object {
        const val ADVENTURE_FILTERS_PARAM = "filter"

        fun newInstance(filters: AdventureFilters): AdventureFiltersDialog {
            return AdventureFiltersDialog().apply {
                arguments = bundleOf(
                        ADVENTURE_FILTERS_PARAM to filters
                )
            }
        }

    }

}