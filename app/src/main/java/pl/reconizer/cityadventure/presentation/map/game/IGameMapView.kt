package pl.reconizer.cityadventure.presentation.map.game

import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.entities.AdventurePoint
import pl.reconizer.cityadventure.domain.entities.Position
import pl.reconizer.cityadventure.presentation.mvp.IView

interface IGameMapView : IView {

    fun requestLocationPermission()
    fun showCurrentLocation(position: Position)
    fun showLocationUnavailable()
    fun showAdventures(adventures: List<Adventure>)
    fun showAdventurePoints(points: List<AdventurePoint>)

}