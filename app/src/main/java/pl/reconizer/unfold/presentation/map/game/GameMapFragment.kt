package pl.reconizer.unfold.presentation.map.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.fragment_game_map.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.common.extensions.toLatLng
import pl.reconizer.unfold.di.Injector
import pl.reconizer.unfold.domain.entities.*
import pl.reconizer.unfold.domain.entities.puzzles.PuzzleResponse
import pl.reconizer.unfold.domain.entities.puzzles.PuzzleType
import pl.reconizer.unfold.presentation.common.BaseFragment
import pl.reconizer.unfold.presentation.common.IViewWithLocation
import pl.reconizer.unfold.presentation.map.IMapView
import pl.reconizer.unfold.presentation.map.IPinMapper
import pl.reconizer.unfold.presentation.map.MapMode
import pl.reconizer.unfold.presentation.map.PinProvider
import pl.reconizer.unfold.presentation.navigation.keys.AdventureStartPointKey
import pl.reconizer.unfold.presentation.navigation.keys.AdventureSummaryKey
import pl.reconizer.unfold.presentation.navigation.keys.MenuKey
import pl.reconizer.unfold.presentation.navigation.keys.SearchKey
import pl.reconizer.unfold.presentation.navigation.keys.puzzles.*
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
        searchButton.setOnClickListener { navigator.goTo(SearchKey()) }

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
        presenter.adventure = arguments?.get(ADVENTURE_PARAM) as MapAdventure?
        presenter.subscribe(this, mapMode)
        presenter.lastLocation?.let {
            mapView.handleNewUserLocation(it.toLatLng())
        }
        mapView.cameraMovedListener = {
            presenter.cameraPositionObserver.onNext(it)
        }
        mapView.pinClickListener = {
            when (it) {
                is MapAdventure -> {
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

    override fun showAdventures(adventures: List<MapAdventure>) {
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
        puzzleResponse.puzzleType?.let {
            navigator.goTo(when(it) {
                PuzzleType.NUMBER_LOCK_3 -> CypherLockPuzzleKey(presenter.adventure!!, point, it)
                PuzzleType.NUMBER_LOCK_4 -> CypherLockPuzzleKey(presenter.adventure!!, point, it)
                PuzzleType.NUMBER_LOCK_5 -> CypherLockPuzzleKey(presenter.adventure!!, point, it)
                PuzzleType.NUMBER_LOCK_6 -> CypherLockPuzzleKey(presenter.adventure!!, point, it)
                PuzzleType.NUMBER_PUSH_LOCK_3 -> NumberPushLockPuzzleKey(presenter.adventure!!, point, it)
                PuzzleType.NUMBER_PUSH_LOCK_4 -> NumberPushLockPuzzleKey(presenter.adventure!!, point, it)
                PuzzleType.NUMBER_PUSH_LOCK_5 -> NumberPushLockPuzzleKey(presenter.adventure!!, point, it)
                PuzzleType.DIRECTION_LOCK_4 -> DirectionLockPuzzleKey(presenter.adventure!!, point, it)
                PuzzleType.DIRECTION_LOCK_6 -> DirectionLockPuzzleKey(presenter.adventure!!, point, it)
                PuzzleType.DIRECTION_LOCK_8 -> DirectionLockPuzzleKey(presenter.adventure!!, point, it)
                PuzzleType.CRYPTEX_LOCK_4 -> CryptexLockPuzzleKey(presenter.adventure!!, point, it)
                PuzzleType.CRYPTEX_LOCK_5 -> CryptexLockPuzzleKey(presenter.adventure!!, point, it)
                PuzzleType.CRYPTEX_LOCK_6 -> CryptexLockPuzzleKey(presenter.adventure!!, point, it)
                PuzzleType.CRYPTEX_LOCK_7 -> CryptexLockPuzzleKey(presenter.adventure!!, point, it)
                else -> DirectionLockPuzzleKey(presenter.adventure!!, point, PuzzleType.DIRECTION_LOCK_4)//TextPuzzleKey(presenter.adventure!!, point, it)
            })
        }
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