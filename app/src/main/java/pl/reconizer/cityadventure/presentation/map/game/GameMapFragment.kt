package pl.reconizer.cityadventure.presentation.map.game

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.fragment_game_map.*
import pl.reconizer.cityadventure.R
import pl.reconizer.cityadventure.common.extensions.toLatLng
import pl.reconizer.cityadventure.di.Injector
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.entities.AdventurePoint
import pl.reconizer.cityadventure.domain.entities.Position
import pl.reconizer.cityadventure.presentation.adventure.startpoint.StartPointFragment
import pl.reconizer.cityadventure.presentation.common.BaseFragment
import pl.reconizer.cityadventure.presentation.map.IMapView
import pl.reconizer.cityadventure.presentation.map.IPinMapper
import pl.reconizer.cityadventure.presentation.map.MapMode
import pl.reconizer.cityadventure.presentation.map.PinProvider
import javax.inject.Inject
import javax.inject.Named

class GameMapFragment : BaseFragment(), IGameMapView {

    @Inject
    lateinit var pinProvider: PinProvider

    @field:[Inject Named("adventure_pin_mapper")]
    lateinit var adventurePinMapper: IPinMapper

    @field:[Inject Named("started_adventure_pin_mapper")]
    lateinit var startedAdventurePinMapper: IPinMapper

    @Inject
    lateinit var presenter: GameMapPresenter

    private val mapMode: MapMode by lazy { (arguments?.get(MAP_MODE_PARAM) as MapMode?) ?: MapMode.ADVENTURES }

    private val mapView: IMapView by lazy { childFragmentManager.findFragmentById(R.id.mapContainer) as IMapView }

    private var locationPermissionDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.buildGameMapComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myLocationButton.setOnClickListener {
            presenter.lastLocation?.let {
                mapView.moveToLocation(it.toLatLng())
            }
        }
        if (mapMode == MapMode.ADVENTURES) {
            mapView.pinMapper = adventurePinMapper
            journalButton.isGone = true
            menuButton.isVisible = true
            scannerButton.isVisible = true
            searchButton.isVisible = true
        } else {
            mapView.pinMapper = startedAdventurePinMapper
            journalButton.isVisible = true
            menuButton.isGone = true
            scannerButton.isGone = true
            searchButton.isGone = true
        }
        mapView.userPin = pinProvider.userPin
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe(this, MapMode.ADVENTURES)
        presenter.lastLocation?.let {
            mapView.handleNewUserLocation(it.toLatLng())
        }
        mapView.cameraMovedListener = {
            presenter.cameraPositionObserver.onNext(it)
        }
        mapView.pinClickListener = {
            when (it) {
                is Adventure -> navigator.goTo(StartPointFragment.newInstance(it))
            }
        }
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        Injector.clearGameMapComponent()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.size != 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                // TODO permissions not granted
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun requestLocationPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            context?.let {
                if (locationPermissionDialog == null) {
                    locationPermissionDialog = AlertDialog.Builder(it)
                            .setMessage(R.string.location_access_explaination)
                            .setPositiveButton(R.string.common_ok) { _, _ ->
                                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST)
                            }
                            .setNegativeButton(R.string.common_close) { _, _ ->
                                activity?.finish() // close the app for now
                            }.create()
                }
                locationPermissionDialog!!.show()
            }
        } else {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST)
        }
    }

    override fun showCurrentLocation(position: Position) {
        mapView.handleNewUserLocation(position.toLatLng())
    }

    override fun showAdventures(adventures: List<Adventure>) {
        mapView.showMarkers(adventures)
    }

    override fun showAdventurePoints(points: List<AdventurePoint>) {
        mapView.showMarkers(points)
    }

    override fun showLocationUnavailable() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        const val MAP_MODE_PARAM = "map_mode"
        const val LOCATION_PERMISSION_REQUEST = 1

        fun newInstance(mapMode: MapMode = MapMode.ADVENTURES): GameMapFragment {
            return GameMapFragment().apply {
                arguments = bundleOf(
                        MAP_MODE_PARAM to mapMode
                )
            }
        }
    }

}