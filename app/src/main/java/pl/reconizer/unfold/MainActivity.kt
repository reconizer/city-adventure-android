package pl.reconizer.unfold

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import com.zhuinden.simplestack.*
import pl.reconizer.unfold.di.Injector
import pl.reconizer.unfold.presentation.common.IViewWithLocation
import pl.reconizer.unfold.presentation.customviews.dialogs.LocationInfoDialogBuilder
import pl.reconizer.unfold.presentation.customviews.dialogs.PrettyDialog
import pl.reconizer.unfold.presentation.navigation.FragmentStateChanger
import pl.reconizer.unfold.presentation.navigation.keys.SplashKey
import javax.inject.Inject

class MainActivity : AppCompatActivity(), IViewWithLocation, StateChanger {

    @Inject
    lateinit var backstackDelegate: BackstackDelegate

    @Inject
    lateinit var fragmentStateChanger: FragmentStateChanger


    val navigator: Backstack
        get() { return backstackDelegate.backstack }

    private var locationInfoDialog: PrettyDialog? = null
    private var locationDeniedCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        Injector.buildMainActivityComponent(R.id.fragmentContainer, this).inject(this)

        backstackDelegate.onCreate(
                savedInstanceState,
                lastCustomNonConfigurationInstance,
                History.single(SplashKey())
        )

        backstackDelegate.registerForLifecycleCallbacks(this)

        super.onCreate(savedInstanceState)

        backstackDelegate.setStateChanger(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        Injector.clearMainActivityComponent()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        if (fragment != null && fragment is OnBackPressedListener) {
            fragment.goBack()
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

    override fun onRetainCustomNonConfigurationInstance(): Any =
            backstackDelegate.onRetainCustomNonConfigurationInstance()

    override fun handleStateChange(stateChange: StateChange, completionCallback: StateChanger.Callback) {
        if (stateChange.topNewState<Any>() == stateChange.topPreviousState()) {
            // no-op
            completionCallback.stateChangeComplete()
            return
        }

        fragmentStateChanger.handleStateChange(stateChange)

        completionCallback.stateChangeComplete()
    }

    companion object {
        const val LOCATION_PERMISSION_REQUEST = 1
    }
}
