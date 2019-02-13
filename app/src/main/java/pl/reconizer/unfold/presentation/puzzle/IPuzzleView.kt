package pl.reconizer.unfold.presentation.puzzle

import pl.reconizer.unfold.presentation.common.IViewWithLocation
import pl.reconizer.unfold.presentation.mvp.IView

interface IPuzzleView : IView, IViewWithLocation {

    fun correctAnswer()

    fun wrongAnswer()

    fun completedAdventure()
}