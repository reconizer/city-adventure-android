package pl.reconizer.cityadventure

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.core.os.bundleOf
import pl.reconizer.cityadventure.di.Injector
import pl.reconizer.cityadventure.presentation.common.IViewWithLocation
import pl.reconizer.cityadventure.presentation.customviews.dialogs.LocationInfoDialogBuilder
import pl.reconizer.cityadventure.presentation.customviews.dialogs.PrettyDialog
import pl.reconizer.cityadventure.presentation.map.MapMode
import pl.reconizer.cityadventure.presentation.map.game.GameMapFragment.Companion.MAP_MODE_PARAM
import pl.reconizer.cityadventure.presentation.navigation.INavigator
import javax.inject.Inject

class MainActivity : AppCompatActivity(), IViewWithLocation {

    @Inject
    lateinit var navigator: INavigator

    private var locationInfoDialog: PrettyDialog? = null
    private var locationDeniedCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Injector.buildMainActivityComponent(R.id.fragmentContainer, this).inject(this)
        navigator.openMapRoot(bundleOf(
                MAP_MODE_PARAM to MapMode.ADVENTURES
        ))

    }

    override fun onDestroy() {
        super.onDestroy()
        Injector.clearMainActivityComponent()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if (fragment != null && fragment is OnBackPressedListener) {
            if (!fragment.goBack() && !navigator.isRoot()) {
                super.onBackPressed()
            } else {
                handleExit()
            }
        } else {
            handleExit()
        }
    }

    private fun handleExit() {
        // TODO double tap to exit
    }

    fun showStatusBar() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    fun hideStatusBar() {
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.size != 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                locationDeniedCounter++
                requestLocationPermission()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun requestLocationPermission() {
        locationInfoDialog?.dismiss()
        locationInfoDialog = null
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            locationInfoDialog = LocationInfoDialogBuilder(this, LocationInfoDialogBuilder.Type.EXPLANATION).apply {
                actionListener = { ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST) }
            }.build()
        } else {
            if (locationDeniedCounter > 0) {
                locationInfoDialog = LocationInfoDialogBuilder(this, LocationInfoDialogBuilder.Type.ACCESS_DENIED).apply {
                    actionListener = { goToLocationPermissionsSettings() }
                }.build()
            } else {
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST)
            }
        }
        locationInfoDialog?.show(supportFragmentManager, "location_alert")
    }

    override fun gpsUnavailable() {
        locationInfoDialog?.dismiss()
        locationInfoDialog = LocationInfoDialogBuilder(this, LocationInfoDialogBuilder.Type.UNABLE_TO_ACCESS).apply {
            actionListener = { goToLocationInterfaceSettings() }
        }.build()
        locationInfoDialog?.show(supportFragmentManager, "location_alert")
    }

    override fun gpsAvailableAgain() {
        locationInfoDialog?.dismiss()
        locationInfoDialog = null
    }

    override fun goToLocationPermissionsSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.fromParts("package", packageName, null)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    override fun goToLocationInterfaceSettings() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    companion object {
        const val LOCATION_PERMISSION_REQUEST = 1
    }
}
