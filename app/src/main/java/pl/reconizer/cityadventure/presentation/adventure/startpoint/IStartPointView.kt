package pl.reconizer.cityadventure.presentation.adventure.startpoint

import pl.reconizer.cityadventure.domain.entities.AdventureStartPoint
import pl.reconizer.cityadventure.presentation.mvp.IView

interface IStartPointView : IView {

    fun show(adventureStartPoint: AdventureStartPoint)

    fun adventureStarted()

}