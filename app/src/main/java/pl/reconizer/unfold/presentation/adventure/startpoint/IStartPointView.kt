package pl.reconizer.unfold.presentation.adventure.startpoint

import pl.reconizer.unfold.domain.entities.AdventureStartPoint
import pl.reconizer.unfold.presentation.mvp.IView

interface IStartPointView : IView {

    fun show(adventureStartPoint: AdventureStartPoint)

    fun adventureStarted()

}