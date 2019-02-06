package pl.reconizer.cityadventure.presentation.puzzle

import pl.reconizer.cityadventure.presentation.common.IViewWithLocation
import pl.reconizer.cityadventure.presentation.mvp.IView

interface IPuzzleView : IView, IViewWithLocation {

    fun correctAnswer()

    fun wrongAnswer()

    fun completedAdventure()
}