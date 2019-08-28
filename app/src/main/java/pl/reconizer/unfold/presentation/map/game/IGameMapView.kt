package pl.reconizer.unfold.presentation.map.game

import pl.reconizer.unfold.domain.entities.MapAdventure
import pl.reconizer.unfold.domain.entities.AdventurePoint
import pl.reconizer.unfold.domain.entities.Position
import pl.reconizer.unfold.domain.entities.PuzzleResponse
import pl.reconizer.unfold.presentation.common.IViewWithLocation
import pl.reconizer.unfold.presentation.mvp.IView

interface IGameMapView : IView, IViewWithLocation {

    fun showCurrentLocation(position: Position)

    fun showAdventures(adventures: List<MapAdventure>)
    fun showAdventurePoints(points: List<AdventurePoint>)

    fun showPuzzle(point: AdventurePoint, puzzleResponse: PuzzleResponse)
    fun showSummary()

}