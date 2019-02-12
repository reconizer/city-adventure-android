package pl.reconizer.cityadventure.presentation.map.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.fragment_game_map.*
import pl.reconizer.cityadventure.R
import pl.reconizer.cityadventure.common.extensions.toLatLng
import pl.reconizer.cityadventure.di.Injector
import pl.reconizer.cityadventure.domain.entities.*
import pl.reconizer.cityadventure.presentation.common.BaseFragment
import pl.reconizer.cityadventure.presentation.common.IViewWithLocation
import pl.reconizer.cityadventure.presentation.map.IMapView
import pl.reconizer.cityadventure.presentation.map.IPinMapper
import pl.reconizer.cityadventure.presentation.map.MapMode
import pl.reconizer.cityadventure.presentation.map.PinProvider
import pl.reconizer.cityadventure.presentation.navigation.keys.AdventureStartPointKey
import pl.reconizer.cityadventure.presentation.navigation.keys.AdventureSummaryKey
import pl.reconizer.cityadventure.presentation.navigation.keys.MenuKey
import pl.reconizer.cityadventure.presentation.navigation.keys.puzzles.NumberPushLockPuzzleKey
import pl.reconizer.cityadventure.presentation.navigation.keys.puzzles.TextPuzzleKey
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

    val mapMode: MapMode
        get() { return (arguments?.get(MAP_MODE_PARAM) as MapMode?) ?: MapMode.ADVENTURES }

    private val adventurePointId: String?
        get() { return arguments?.get(ADVENTURE_POINT_ID_PARAM) as String? }

    private val mapView: IMapView
        get() { return childFragmentManager.findFragmentById(R.id.mapContainer) as IMapView }

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
        journalButton.setOnClickListener { navigator.goBack() }
        locationCheckerButton.setOnClickListener { presenter.checkLocation() }
        menuButton.setOnClickListener { navigator.goTo(MenuKey()) }
        if (mapMode == MapMode.ADVENTURES) {
            mapView.pinMapper = adventurePinMapper
            adventuresButtonsGroup.isVisible = true
            adventureButtonsGroup.isGone = true
        } else {
            mapView.pinMapper = startedAdventurePinMapper
            adventuresButtonsGroup.isGone = true
            adventureButtonsGroup.isVisible = true
        }
        mapView.userPin = pinProvider.userPin
        mapView.clearMarkers()
    }

    override fun onResume() {
        super.onResume()
        presenter.adventure = arguments?.get(ADVENTURE_PARAM) as Adventure?
        presenter.subscribe(this, mapMode)
        presenter.lastLocation?.let {
            mapView.handleNewUserLocation(it.toLatLng())
        }
        mapView.cameraMovedListener = {
            presenter.cameraPositionObserver.onNext(it)
        }
        mapView.pinClickListener = {
            when (it) {
                is Adventure -> {
                    //navigator.leaveMap()
                    navigator.goTo(AdventureStartPointKey(it))
                }
                is AdventurePoint -> {
                    if (!it.isCompleted) {
                        presenter.resolvePoint(it)
                    }
                }
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

    override fun goBack(): Boolean {
        return if (mapMode == MapMode.STARTED_ADVENTURE) {
            navigator.goBack()
            true
        } else {
            false
        }
    }

    override fun requestLocationPermission() {
        (activity as IViewWithLocation?)?.requestLocationPermission()
    }

    override fun gpsUnavailable() {
        (activity as IViewWithLocation?)?.gpsUnavailable()
    }

    override fun gpsAvailableAgain() {
        (activity as IViewWithLocation?)?.gpsAvailableAgain()
    }

    override fun goToLocationPermissionsSettings() {
        (activity as IViewWithLocation?)?.goToLocationPermissionsSettings()
    }

    override fun goToLocationInterfaceSettings() {
        (activity as IViewWithLocation?)?.goToLocationInterfaceSettings()
    }

    override fun showCurrentLocation(position: Position) {
        mapView.handleNewUserLocation(position.toLatLng())
    }

    override fun showAdventures(adventures: List<Adventure>) {
        mapView.showMarkers(adventures)
    }

    override fun showAdventurePoints(points: List<AdventurePoint>) {
        mapView.showMarkers(points)
        adventurePointId?.let {id ->
            val centeredPoint = points.find { point ->
                point.id == id
            }
            centeredPoint?.let {
                mapView.moveToLocation(it.position.toLatLng())
            }
        }
    }

    override fun showPuzzle(point: AdventurePoint, puzzleResponse: PuzzleResponse) {
        navigator.goTo(when(puzzleResponse.puzzleType) {
            PuzzleType.NUMBER_PUSH_LOCK_3 -> NumberPushLockPuzzleKey(presenter.adventure!!, point, 3)
            PuzzleType.NUMBER_PUSH_LOCK_4 -> NumberPushLockPuzzleKey(presenter.adventure!!, point, 4)
            PuzzleType.NUMBER_PUSH_LOCK_5 -> NumberPushLockPuzzleKey(presenter.adventure!!, point, 5)
            else -> TextPuzzleKey(presenter.adventure!!, point)
        })
    }

    override fun showSummary() {
        navigator.goTo(AdventureSummaryKey(presenter.adventure!!))
    }

    companion object {
        const val MAP_MODE_PARAM = "map_mode"
        const val ADVENTURE_PARAM = "adventure"
        const val ADVENTURE_POINT_ID_PARAM = "adventure_point_id"
    }

}