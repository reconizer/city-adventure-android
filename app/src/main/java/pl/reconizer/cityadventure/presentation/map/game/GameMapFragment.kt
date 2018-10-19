package pl.reconizer.cityadventure.presentation.map.game

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_game_map.*
import pl.reconizer.cityadventure.R
import pl.reconizer.cityadventure.di.Injector
import pl.reconizer.cityadventure.presentation.common.BaseFragment
import pl.reconizer.cityadventure.presentation.map.IMapView
import javax.inject.Inject

class GameMapFragment : BaseFragment(), IGameMapView {

    @Inject
    lateinit var presenter: GameMapPresenter

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
                mapView.moveToLocation(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe(this)
        presenter.lastLocation?.let {
            mapView.handleNewUserLocation(it)
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
                // TODO start listening for location
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun withStatusBar(): Boolean {
        return false
    }

    override fun requestLocationPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            context?.let {
                if (locationPermissionDialog == null) {
                    locationPermissionDialog = AlertDialog.Builder(it)
                            .setMessage(R.string.location_permission)
                            .setPositiveButton(R.string.ok) { _, _ ->
                                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST)
                            }
                            .setNegativeButton(R.string.close) { _, _ ->
                                activity?.finish() // close the app for now
                            }.create()
                }
                locationPermissionDialog!!.show()
            }
        } else {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST)
        }
    }

    override fun showCurrentLocation(location: LatLng) {
        mapView.handleNewUserLocation(location)
    }

    override fun showLocationUnavailable() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        const val LOCATION_PERMISSION_REQUEST = 1

        fun newInstance(): GameMapFragment {
            return GameMapFragment()
        }
    }

}