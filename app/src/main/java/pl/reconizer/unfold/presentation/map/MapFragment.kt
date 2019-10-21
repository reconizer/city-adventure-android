package pl.reconizer.unfold.presentation.map

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import androidx.annotation.DrawableRes
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.maps.android.SphericalUtil
import pl.reconizer.unfold.R
import pl.reconizer.unfold.common.extensions.toLatLng
import pl.reconizer.unfold.domain.entities.IPositionable
import kotlin.math.abs

class MapFragment : SupportMapFragment(), IMapView {

    private var googleMap: GoogleMap? = null

    private var userMarker: Marker? = null
    private var markers: MutableList<Marker> = mutableListOf()

    private var currentLocation: LatLng? = null

    private val overlays = mutableMapOf<Pair<Int, Int>, GroundOverlay>()
    private var lastMapCenter: LatLng? = null
    private var lastMapZoom: Float = 0f

    override var pinMapper: IPinMapper? = null
    override var userPinMapper: IPinMapper? = null

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

    override fun isMapReady(): Boolean {
        return googleMap != null
    }

    override fun moveToLocation(location: LatLng) {
        googleMap?.animateCamera(CameraUpdateFactory.newLatLng(LatLng(
                location.latitude,
                location.longitude
        )))
    }

    override fun handleNewUserLocation(location: LatLng) {
        if (userMarker == null) createUserMarker(location)
        userMarker?.position = location
        currentLocation = location.also {
            if (currentLocation == null) {
                moveToLocation(location)
            }
        }
    }

    override fun showMarkers(positionables: List<IPositionable>) {
        clearMarkers()
        positionables.forEach {
            markers.add(googleMap!!.addMarker(MarkerOptions()
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

    private fun configure(map: GoogleMap) {
        googleMap = map
        overlayBitmapDescriptor = BitmapDescriptorFactory.fromBitmap(overlayBitmap)
        if (googleMap!!.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.google_map_style))) {
            googleMap!!.apply {
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
            Log.e(TAG, "Map style parsing failed")
        }
        currentLocation?.let {
            handleNewUserLocation(it)
        }

        // when camera is moving
        googleMap!!.setOnCameraMoveListener {
            cameraMoveListener?.invoke(CameraDetails(
                    googleMap!!.cameraPosition.target,
                    googleMap!!.cameraPosition.zoom
            ))
            if (view != null && shouldUpdateOverlays()) {
                updateOverlays()
            }
        }

        // when camera stopped moving
        googleMap!!.setOnCameraIdleListener {
            cameraMovedListener?.invoke(CameraDetails(
                    googleMap!!.cameraPosition.target,
                    googleMap!!.cameraPosition.zoom
            ))
        }

        googleMap!!.setOnMarkerClickListener {
            if (it.tag is IPositionable) {
                pinClickListener?.invoke(it.tag as IPositionable)
                true
            } else {
                false
            }
        }

        if (currentLocation != null) {
            moveToLocation(currentLocation!!)
        }
    }

    private fun createUserMarker(latLng: LatLng) {
        userMarker = googleMap?.addMarker(MarkerOptions()
                .zIndex(1f)
                .position(latLng)
                .anchor(0.5f, 1f)
                .icon(userPinMapper?.determinePin())
        )
    }

    private fun updateOverlays() {
        lastMapCenter = googleMap!!.cameraPosition.target
        lastMapZoom = googleMap!!.cameraPosition.zoom
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
                    overlays[idx] = googleMap!!.addGroundOverlay(GroundOverlayOptions()
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
        val currentZoom = googleMap!!.cameraPosition.zoom
        return abs(lastMapZoom - currentZoom) > 1 ||
                (lastMapZoom != currentZoom && (currentZoom == MAX_ZOOM || currentZoom == MIN_ZOOM))
    }

    private fun didMoveOutOfBounds(): Boolean {
        val tilesPerLongestEdge =  view!!.height / overlayBitmap.height
        val southWestLocation = overlays[OVERLAYS_WIDTH - tilesPerLongestEdge + 1 to tilesPerLongestEdge / 2]!!.position
        val northEastLocation = overlays[tilesPerLongestEdge / 2 to OVERLAYS_WIDTH - tilesPerLongestEdge + 1]!!.position

        return !LatLngBounds(southWestLocation, northEastLocation).contains(googleMap!!.cameraPosition.target)
    }

    private fun calculateTileWidth(): Double {
        val centerScreen = googleMap!!.projection.toScreenLocation(googleMap!!.cameraPosition.target)
        val tileEdge = Point(centerScreen.x - overlayBitmap.width / 2, centerScreen.y)
        return 2 * SphericalUtil.computeDistanceBetween(googleMap!!.projection.fromScreenLocation(tileEdge), googleMap!!.cameraPosition.target)
    }

    companion object {
        const val TAG = "MapFragment"

        const val MIN_ZOOM = 15f
        const val MAX_ZOOM = 18f

        const val OVERLAYS_WIDTH = 9
        const val OVERLAY_TRANSPARENCY = 0.75f
    }

}