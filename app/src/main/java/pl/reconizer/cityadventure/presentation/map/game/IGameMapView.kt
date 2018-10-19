package pl.reconizer.cityadventure.presentation.map.game

import com.google.android.gms.maps.model.LatLng
import pl.reconizer.cityadventure.presentation.mvp.IView

interface IGameMapView : IView {

    fun requestLocationPermission()
    fun showCurrentLocation(location: LatLng)
    fun showLocationUnavailable()

}