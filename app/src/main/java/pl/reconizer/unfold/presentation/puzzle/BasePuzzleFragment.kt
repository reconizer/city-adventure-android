package pl.reconizer.unfold.presentation.puzzle

import android.os.Bundle
import com.zhuinden.simplestack.StateChange
import pl.reconizer.unfold.R
import pl.reconizer.unfold.common.extensions.isFragmentOnStack
import pl.reconizer.unfold.common.extensions.performErrorHapticFeedback
import pl.reconizer.unfold.common.extensions.performSuccessHapticFeedback
import pl.reconizer.unfold.di.Injector
import pl.reconizer.unfold.domain.entities.MapAdventure
import pl.reconizer.unfold.domain.entities.AdventurePoint
import pl.reconizer.unfold.domain.entities.puzzles.PuzzleType
import pl.reconizer.unfold.presentation.common.BaseFragment
import pl.reconizer.unfold.presentation.common.IViewWithLocation
import pl.reconizer.unfold.presentation.customviews.dialogs.ErrorDialogBuilder
import pl.reconizer.unfold.presentation.customviews.dialogs.PrettyDialog
import pl.reconizer.unfold.presentation.navigation.keys.AdventureSummaryKey
import pl.reconizer.unfold.presentation.navigation.keys.BaseKey
import javax.inject.Inject

open class BasePuzzleFragment : BaseFragment(), IPuzzleView, IViewWithLocation {

    @Inject
    lateinit var presenter: PuzzlePresenter

    private var wrongAnswerInfoDialog: PrettyDialog? = null

    private var correctAnswerInfoDialog: PrettyDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adventure = arguments?.get(ADVENTURE_PARAM) as MapAdventure?
        val adventurePoint = arguments?.get(ADVENTURE_POINT_PARAM) as AdventurePoint?
        val puzzleType = arguments?.get(PUZZLE_TYPE_PARAM) as PuzzleType?
        Injector.buildPuzzleComponent(
                adventure!!,
                adventurePoint!!,
                puzzleType!!
        ).inject(this)
    }

    override fun onPause() {
        super.onPause()
        wrongAnswerInfoDialog?.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        Injector.clearPuzzleComponent()
    }

    override fun requestLocationPermission() {
        (activity as IViewWithLocation?)?.requestLocationPermission()
    }

    override fun gpsUnavailable() {
        (activity as IViewWithLocation?)?.gpsUnavailable()
    }

    override fun gpsAvailableAgain() {
        (activity as IViewWithLocation?)?.gpsAvailableAgain()
    }

    override fun goToLocationPermissionsSettings() {
        (activity as IViewWithLocation?)?.goToLocationPermissionsSettings()
    }

    override fun goToLocationInterfaceSettings() {
        (activity as IViewWithLocation?)?.goToLocationInterfaceSettings()
    }

    override fun correctAnswer() {
        context?.performSuccessHapticFeedback()
        if (!childFragmentManager.isFragmentOnStack(CORRECT_ANSWER_DIALOG_TAG)){
            correctAnswerInfoDialog = PrettyDialog().apply {
                isCancelable = false
                headerText = this@BasePuzzleFragment.resources.getString(R.string.puzzle_correct_answer_title)
                contentText = this@BasePuzzleFragment.resources.getString(R.string.puzzle_correct_answer_message)
                firstButtonText = this@BasePuzzleFragment.resources.getString(R.string.puzzle_correct_answer_button)
                firstButtonClickListener = { goToJournal() }
                closeButtonClickListener = { goToJournal() }
            }
            correctAnswerInfoDialog?.show(childFragmentManager, CORRECT_ANSWER_DIALOG_TAG)
        }
    }

    override fun wrongAnswer() {
        context?.performErrorHapticFeedback()
        showWrongAnswerDialog()
    }

    override fun completedAdventure() {
        context?.performSuccessHapticFeedback()
        if (!childFragmentManager.isFragmentOnStack(CORRECT_ANSWER_DIALOG_TAG)){
            correctAnswerInfoDialog = PrettyDialog().apply {
                isCancelable = false
                headerText = this@BasePuzzleFragment.resources.getString(R.string.puzzle_correct_answer_title)
                contentText = this@BasePuzzleFragment.resources.getString(R.string.puzzle_correct_answer_message_summary)
                firstButtonText = this@BasePuzzleFragment.resources.getString(R.string.puzzle_correct_answer_summary_button)
                firstButtonClickListener = { goToSummary() }
                closeButtonClickListener = { goToSummary() }
            }
            correctAnswerInfoDialog?.show(childFragmentManager, CORRECT_ANSWER_DIALOG_TAG)
        }
    }

    fun showWrongAnswerDialog() {
        wrongAnswerInfoDialog?.dismiss()
        context?.let {
            wrongAnswerInfoDialog = ErrorDialogBuilder(it)
                    .setErrorMessage(resources.getString(R.string.puzzle_wrong_answer))
                    .build()
            wrongAnswerInfoDialog?.show(childFragmentManager, "wrong_answer")
        }
    }

    private fun goToJournal() {
        navigator.goTo(navigator.fromTop<BaseKey>(2))
    }

    private fun goToSummary() {
        // replaces in order to prevent going back to a puzzle screen
        navigator.replaceTop(
                AdventureSummaryKey(arguments?.get(ADVENTURE_PARAM) as MapAdventure),
                StateChange.REPLACE
        )
    }

    companion object {
        const val ADVENTURE_PARAM = "adventure"
        const val ADVENTURE_POINT_PARAM = "adventure_point"
        const val PUZZLE_TYPE_PARAM = "puzzle_type"

        const val CORRECT_ANSWER_DIALOG_TAG = "correct_answer"
    }
}