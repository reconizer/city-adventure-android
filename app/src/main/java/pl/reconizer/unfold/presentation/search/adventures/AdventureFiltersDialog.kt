package pl.reconizer.unfold.presentation.search.adventures

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_adventure_filters.*
import kotlinx.android.synthetic.main.dialog_adventure_filters.view.*
import kotlinx.android.synthetic.main.dialog_puzzle_tutorial.view.dialogHeader
import pl.reconizer.unfold.R
import pl.reconizer.unfold.domain.entities.DifficultyLevel
import java.lang.IllegalArgumentException

class AdventureFiltersDialog : DialogFragment() {

    lateinit var filtersState: AdventureFilters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        filtersState = arguments?.get(ADVENTURE_FILTERS_PARAM) as AdventureFilters? ?: throw IllegalStateException("AdventureFilters object is required")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_adventure_filters, null, true)
        dialogView.dialogHeader.closeButtonClickListener = { dismiss() }
        dialogView?.apply {
            rangeMinValue.text = "%d".format(filtersState.minRange.toInt())
            rangeMaxValue.text = "%d".format(filtersState.maxRange.toInt())
            rangeCheckbox.isChecked = filtersState.isRangeActive
            difficultyLevelCheckbox.isChecked = filtersState.isDifficultyLevelActive
            range.progress = (filtersState.range * 100).toInt()
            rangeIndicator.text = formatRangeIndicator()
            if (filtersState.difficultyLevel == null) {
                difficultyLevelGroup.clearCheck()
            } else {
                difficultyLevelGroup.check(difficultyLevelToRadioButtonId(filtersState.difficultyLevel!!))
            }

            rangeCheckbox.setOnCheckedChangeListener { _, isChecked ->
                filtersState.isRangeActive = isChecked
                rangeFilterActivityChanged(this)
            }

            difficultyLevelCheckbox.setOnCheckedChangeListener { _, isChecked ->
                filtersState.isDifficultyLevelActive = isChecked
                difficultyFilterActivityChanged(this)
            }

            range.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    filtersState.range = progress / 100f
                    rangeIndicator.text = formatRangeIndicator()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}

            })

            rangeFilterActivityChanged(this)
            difficultyFilterActivityChanged(this)
        }

        val dialog = AlertDialog.Builder(requireContext(), R.style.CustomDialog).run {
            setView(dialogView)
            create()
        }
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.decorView?.setBackgroundResource(android.R.color.transparent)
        return dialog
    }

    private fun radioButtonIdToDifficultyLevel(id: Int): DifficultyLevel {
        return when(id) {
            R.id.easyLevel -> DifficultyLevel.EASY
            R.id.mediumLevel -> DifficultyLevel.MEDIUM
            R.id.hardLevel -> DifficultyLevel.HARD
            else -> throw IllegalArgumentException("Unknown radio button id.")
        }
    }

    private fun difficultyLevelToRadioButtonId(difficultyLevel: DifficultyLevel): Int {
        return when(difficultyLevel) {
            DifficultyLevel.EASY -> R.id.easyLevel
            DifficultyLevel.MEDIUM -> R.id.mediumLevel
            DifficultyLevel.HARD -> R.id.hardLevel
        }
    }

    private fun formatRangeIndicator(): String {
        return "${resources.getString(R.string.sort_range)} (${"%.2f".format((filtersState.maxRange - filtersState.minRange) * filtersState.range + filtersState.minRange)} km)"
    }

    private fun rangeFilterActivityChanged(rootView: View) {
        rootView.range.isEnabled = filtersState.isRangeActive
        rootView.rangeIndicator.isEnabled = filtersState.isRangeActive
        rootView.rangeMinValue.isEnabled = filtersState.isRangeActive
        rootView.rangeMaxValue.isEnabled = filtersState.isRangeActive
    }

    private fun difficultyFilterActivityChanged(rootView: View) {
        rootView.difficultyLevelGroup.isEnabled = filtersState.isDifficultyLevelActive
        rootView.easyLevel.isEnabled = filtersState.isDifficultyLevelActive
        rootView.mediumLevel.isEnabled = filtersState.isDifficultyLevelActive
        rootView.hardLevel.isEnabled = filtersState.isDifficultyLevelActive
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