package pl.reconizer.unfold.presentation.common

interface IViewWithLocation {

    fun requestLocationPermission()

    fun gpsUnavailable()

    fun gpsAvailableAgain()

    fun goToLocationPermissionsSettings()

    fun goToLocationInterfaceSettings()

}