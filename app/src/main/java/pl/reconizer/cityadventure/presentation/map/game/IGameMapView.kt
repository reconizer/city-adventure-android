package pl.reconizer.cityadventure.presentation.map.game

import android.location.Location
import pl.reconizer.cityadventure.presentation.mvp.IView

interface IGameMapView : IView {

    fun requestLocationPermission()
    fun showCurrentLocation(location: Location)
    fun showLocationUnavailable()

}