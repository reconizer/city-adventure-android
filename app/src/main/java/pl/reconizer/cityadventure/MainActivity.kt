package pl.reconizer.cityadventure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.core.os.bundleOf
import pl.reconizer.cityadventure.di.Injector
import pl.reconizer.cityadventure.presentation.map.MapMode
import pl.reconizer.cityadventure.presentation.map.game.GameMapFragment
import pl.reconizer.cityadventure.presentation.map.game.GameMapFragment.Companion.MAP_MODE_PARAM
import pl.reconizer.cityadventure.presentation.navigation.INavigator
import java.nio.channels.FileChannel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator: INavigator

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
}
