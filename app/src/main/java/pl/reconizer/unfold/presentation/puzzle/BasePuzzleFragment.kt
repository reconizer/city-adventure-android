package pl.reconizer.unfold.presentation.puzzle

import android.os.Bundle
import com.zhuinden.simplestack.StateChange
import pl.reconizer.unfold.R
import pl.reconizer.unfold.di.Injector
import pl.reconizer.unfold.domain.entities.MapAdventure
import pl.reconizer.unfold.domain.entities.AdventurePoint
import pl.reconizer.unfold.domain.entities.puzzles.PuzzleType
import pl.reconizer.unfold.presentation.common.BaseFragment
import pl.reconizer.unfold.presentation.common.IViewWithLocation
import pl.reconizer.unfold.presentation.customviews.dialogs.ErrorDialogBuilder
import pl.reconizer.unfold.presentation.customviews.dialogs.PrettyDialog
import pl.reconizer.unfold.presentation.navigation.keys.AdventureSummaryKey
import javax.inject.Inject

open class BasePuzzleFragment : BaseFragment(), IPuzzleView, IViewWithLocation {

    @Inject
    lateinit var presenter: PuzzlePresenter

    private var wrongAnswerInfoDialog: PrettyDialog? = null

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
        navigator.goBack() // opens the map
    }

    override fun wrongAnswer() {
        showWrongAnswerDialog()
    }

    override fun completedAdventure() {
        // replaces in order to prevent going back to a puzzle screen
        navigator.replaceTop(
                AdventureSummaryKey(arguments?.get(ADVENTURE_PARAM) as MapAdventure),
                StateChange.REPLACE
        )
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

    companion object {
        const val ADVENTURE_PARAM = "adventure"
        const val ADVENTURE_POINT_PARAM = "adventure_point"
        const val PUZZLE_TYPE_PARAM = "puzzle_type"
    }
}