package pl.reconizer.unfold.presentation.map

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.SphericalUtil
import pl.reconizer.unfold.R
import pl.reconizer.unfold.common.extensions.toLatLng
import pl.reconizer.unfold.domain.entities.IPositionable
import pl.reconizer.unfold.domain.entities.Position
import timber.log.Timber
import kotlin.math.abs
import kotlin.math.absoluteValue

class MapFragment : SupportMapFragment(), IMapView {

    private lateinit var googleMap: GoogleMap

    private var userMarker: Marker? = null
    private var markers: MutableList<Marker> = mutableListOf()

    private var markerRange: Circle? = null

    private var currentLocation: LatLng? = null

    private val overlays = mutableMapOf<Pair<Int, Int>, GroundOverlay>()
    private var lastMapCenter: LatLng? = null
    private var lastMapZoom: Float = 0f

    override var pinMapper: IPinMapper? = null
    override var userPinMapper: IPinMapper? = null

    override var isMapReady: Boolean = false
        private set

    @DrawableRes
    override var overlayDrawableRes: Int = R.drawable.map_overlay
    private lateinit var overlayBitmap: Bitmap
    private lateinit var overlayBitmapDescriptor: BitmapDescriptor

    override var pinClickListener: ((pin: IPositionable) -> Unit)? = null
    override var cameraMoveListener: ((cameraDetails: CameraDetails) -> Unit)? = null
    override var cameraMovedListener: ((cameraDetails: CameraDetails) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overlayBitmap = BitmapFactory.decodeResource(resources, overlayDrawableRes)
        getMapAsync(this::configure)
    }

    override fun moveToLocation(location: LatLng) {
        if (isMapReady) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(LatLng(
                    location.latitude,
                    location.longitude
            )))
        }
    }

    override fun handleNewUserLocation(location: LatLng) {
        if (userMarker == null) createUserMarker(location)
        userMarker?.position = location
        currentLocation = location.also {
            // Center map to user's location on first location update which probably means that
            // this is a first map load and user didn't interact with the map
            if (currentLocation == null) {
                moveToLocation(location)
            }
        }
    }

    override fun showMarkers(positionables: List<IPositionable>) {
        clearMarkers()
        positionables.forEach {
            markers.add(googleMap.addMarker(MarkerOptions()
                    .position(it.position.toLatLng())
                    .icon(pinMapper?.determinePin(it)))
                    .apply {
                        tag = it
                    }
            )
        }
    }

    override fun clearMarkers() {
        markers.forEach { it.remove() }
        markers.clear()
    }

    override fun showMarkerRange(position: Position, range: Double) {
        clearMarkerRange()
        markerRange = googleMap.addCircle(
                CircleOptions()
                        .center(position.toLatLng())
                        .radius(range)
                        .strokeWidth(8f)
                        .fillColor(ContextCompat.getColor(requireContext(), R.color.adventurePointRangeColor))
                        .strokeColor(ContextCompat.getColor(requireContext(), R.color.adventurePointRangeStrokeColor))

        )
    }

    override fun clearMarkerRange() {
        markerRange?.remove()
        markerRange = null
    }

    private fun configure(map: GoogleMap) {
        googleMap = map
        isMapReady = true
        overlayBitmapDescriptor = BitmapDescriptorFactory.fromBitmap(overlayBitmap)
        if (googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.google_map_style))) {
            googleMap.apply {
                setMinZoomPreference(MIN_ZOOM)
                setMaxZoomPreference(MAX_ZOOM)
                isBuildingsEnabled = false
                isTrafficEnabled = false
                isIndoorEnabled = false

                uiSettings.apply {
                    isTiltGesturesEnabled = false
                    isZoomGesturesEnabled = true
                    isScrollGesturesEnabled = true
                    isRotateGesturesEnabled = true
                    isIndoorLevelPickerEnabled = false
                    isCompassEnabled = false
                    isZoomControlsEnabled = false
                    isMapToolbarEnabled = false
                }
            }
        } else {
            Timber.e("Map style parsing failed")
        }
        currentLocation?.let {
            handleNewUserLocation(it)
        }

        // when camera is moving
        googleMap.setOnCameraMoveListener {
            Timber.i("map camera: is moving")
            cameraMoveListener?.invoke(CameraDetails(
                    googleMap.cameraPosition.target,
                    googleMap.cameraPosition.zoom
            ))
            if (shouldUpdateOverlays()) {
                updateOverlays()
            }
        }

        // when camera stopped moving
        googleMap.setOnCameraIdleListener {
            Timber.i("map camera: stopped moving")
            cameraMovedListener?.invoke(CameraDetails(
                    googleMap.cameraPosition.target,
                    googleMap.cameraPosition.zoom
            ))
            if (didZoomChanged() || didMoveOutOfBounds() || didTileWidthChanged()) {
                updateOverlays()
            }
        }

        googleMap.setOnMarkerClickListener {
            if (it.tag is IPositionable) {
                Timber.i("map: clicked on adventure marker")
                pinClickListener?.invoke(it.tag as IPositionable)
            } else if (it == userMarker) {
                Timber.i("map: clicked on user marker")
            } else {
                Timber.i("map: clicked on something else")
            }
            true
        }

        currentLocation?.let {
            moveToLocation(it)
        }

        updateOverlays()
    }

    private fun createUserMarker(latLng: LatLng) {
        if (isMapReady) {
            userMarker = googleMap.addMarker(MarkerOptions()
                    .zIndex(1f)
                    .position(latLng)
                    .anchor(0.5f, 1f)
                    .icon(userPinMapper?.determinePin())
            )
        }
    }

    private fun updateOverlays() {
        lastMapCenter = googleMap.cameraPosition.target
        lastMapZoom = googleMap.cameraPosition.zoom
        val tileSize = calculateTileWidth()
        val northWestCenter = LatLng(
                SphericalUtil.computeOffset(lastMapCenter, tileSize * OVERLAYS_WIDTH / 2, 0.0).latitude,
                SphericalUtil.computeOffset(lastMapCenter, tileSize * OVERLAYS_WIDTH / 2, 270.0).longitude
        )
        for (i in 0 until OVERLAYS_WIDTH) {
            for (j in 0 until OVERLAYS_WIDTH) {
                val idx = i to j
                val tileCenter = LatLng(
                        SphericalUtil.computeOffset(northWestCenter, i * tileSize, 180.0).latitude,
                        SphericalUtil.computeOffset(northWestCenter, j * tileSize, 90.0).longitude
                )
                if (overlays[idx] == null) {
                    overlays[idx] = googleMap.addGroundOverlay(GroundOverlayOptions()
                            .position(tileCenter, tileSize.toFloat())
                            .image(overlayBitmapDescriptor)
                            .transparency(OVERLAY_TRANSPARENCY))
                } else {
                    overlays[idx]?.apply {
                        position = tileCenter
                        setDimensions(tileSize.toFloat())
                    }
                }
            }
        }
    }

    private fun shouldUpdateOverlays(): Boolean {
        return overlays.isEmpty() || didZoomChangeEnough() || didMoveOutOfBounds()
    }

    private fun didZoomChangeEnough(): Boolean {
        val currentZoom = googleMap.cameraPosition.zoom
        return (lastMapZoom - currentZoom).absoluteValue >= 0.5 ||
                (lastMapZoom != currentZoom && (currentZoom == MAX_ZOOM || currentZoom == MIN_ZOOM))
    }

    private fun didZoomChanged(): Boolean {
        return lastMapZoom != googleMap.cameraPosition.zoom
    }

    private fun didMoveOutOfBounds(): Boolean {
        val tilesPerLongestEdge =  maxOf(view?.height ?: 0, view?.width ?: 0) / overlayBitmap.height
        val southWestLocation = overlays[OVERLAYS_WIDTH - tilesPerLongestEdge + 1 to tilesPerLongestEdge / 2]!!.position
        val northEastLocation = overlays[tilesPerLongestEdge / 2 to OVERLAYS_WIDTH - tilesPerLongestEdge + 1]!!.position

        return !LatLngBounds(southWestLocation, northEastLocation).contains(googleMap.cameraPosition.target)
    }

    private fun didTileWidthChanged(): Boolean {
        if (overlays.isEmpty()) {
            return true
        }
        val currentWidth: Float = overlays[0 to 0]?.width ?: 0f
        return (currentWidth - calculateTileWidth()).absoluteValue >= OVERLAY_TILE_WIDTH_DIFFERENCE_THRESHOLD
    }

    private fun calculateTileWidth(): Double {
        val centerScreen = googleMap.projection.toScreenLocation(googleMap.cameraPosition.target)
        val tileEdge = Point(centerScreen.x - overlayBitmap.width / 2, centerScreen.y)
        return 2 * SphericalUtil.computeDistanceBetween(googleMap.projection.fromScreenLocation(tileEdge), googleMap.cameraPosition.target)
    }

    companion object {
        const val MIN_ZOOM = 15f
        const val MAX_ZOOM = 18f

        const val OVERLAYS_WIDTH = 9
        const val OVERLAY_TRANSPARENCY = 0.75f

        const val OVERLAY_TILE_WIDTH_DIFFERENCE_THRESHOLD = 1f
    }

}