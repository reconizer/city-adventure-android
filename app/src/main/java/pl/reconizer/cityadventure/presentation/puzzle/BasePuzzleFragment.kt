package pl.reconizer.cityadventure.presentation.puzzle

import android.os.Bundle
import pl.reconizer.cityadventure.R
import pl.reconizer.cityadventure.di.Injector
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.entities.AdventurePoint
import pl.reconizer.cityadventure.domain.entities.PuzzleType
import pl.reconizer.cityadventure.presentation.common.BaseFragment
import pl.reconizer.cityadventure.presentation.common.IViewWithLocation
import pl.reconizer.cityadventure.presentation.customviews.dialogs.ErrorDialogBuilder
import pl.reconizer.cityadventure.presentation.customviews.dialogs.PrettyDialog
import pl.reconizer.cityadventure.presentation.navigation.keys.AdventureSummaryKey
import pl.reconizer.cityadventure.presentation.navigation.keys.MapKey
import javax.inject.Inject

open class BasePuzzleFragment : BaseFragment(), IPuzzleView, IViewWithLocation {

    @Inject
    lateinit var presenter: PuzzlePresenter

    private var wrongAnswerInfoDialog: PrettyDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val adventure = arguments?.get(ADVENTURE_PARAM) as Adventure?
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
        navigator.goTo(MapKey.Builder.buildAdventureMapKey(
                adventure = presenter.adventure,
                adventurePointId = presenter.adventurePoint.id
        ))
    }

    override fun wrongAnswer() {
        showWrongAnswerDialog()
    }

    override fun completedAdventure() {
        navigator.goTo(AdventureSummaryKey(
                arguments?.get(ADVENTURE_PARAM) as Adventure
        ))
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