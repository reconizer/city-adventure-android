package pl.reconizer.unfold.presentation.map.game

import pl.reconizer.unfold.domain.entities.MapAdventure
import pl.reconizer.unfold.domain.entities.AdventurePoint
import pl.reconizer.unfold.domain.entities.AdventureStartPoint
import pl.reconizer.unfold.domain.entities.Position
import pl.reconizer.unfold.domain.entities.puzzles.PuzzleResponse
import pl.reconizer.unfold.presentation.common.IViewWithLocation
import pl.reconizer.unfold.presentation.mvp.IView

interface IGameMapView : IView, IViewWithLocation {

    fun showCurrentLocation(position: Position)

    fun showAdventures(adventures: List<MapAdventure>)
    fun showAdventure(adventureStartPoint: AdventureStartPoint)
    fun showAdventurePoints(points: List<AdventurePoint>)
    fun showNumberOfActiveAdventures(n: Int)

    fun updateAdventureTimer(milliseconds: Long)
    fun showPuzzle(point: AdventurePoint, puzzleResponse: PuzzleResponse)
    fun finishAdventure()

}