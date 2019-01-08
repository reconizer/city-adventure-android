package pl.reconizer.cityadventure.presentation.navigation

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import pl.reconizer.cityadventure.presentation.map.game.GameMapFragment

class Navigator(
        @IdRes private val container: Int,
        private val fragmentManager: FragmentManager
) : INavigator {

    private val mapFragment = GameMapFragment()

    override fun goTo(fragment: Fragment, addOnStack: Boolean, transitionElement: SharedTransitionElement?) {
        fragmentManager.beginTransaction().apply {
            if (transitionElement != null) {
                addSharedElement(transitionElement.view, transitionElement.transitionName)
            }
            if (addOnStack) {
                addToBackStack(null)
            }
        }
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(container, fragment)
                .commit()
    }

    override fun openOver(fragment: Fragment, transitionElement: SharedTransitionElement?) {
        fragmentManager.beginTransaction().apply {
            if (transitionElement != null) {
                addSharedElement(transitionElement.view, transitionElement.transitionName)
            }
        }
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
                .add(container, fragment)
                .addToBackStack(null)
                .commit()
    }

    override fun goBack() {
        if (!isRoot()) {
            fragmentManager.popBackStack()
        }
    }

    override fun isRoot(): Boolean {
        return fragmentManager.backStackEntryCount <= 1
    }

    override fun showMap(bundle: Bundle) {
        mapFragment.arguments = bundle
        if (mapFragment.isDetached) {
            attachMap()
        } else {
            openMapRoot(bundle)
        }

    }

    override fun leaveMap() {
        if (mapFragment.isAdded) {
            fragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
                    .detach(mapFragment)
                    .commit()
        }
    }

    override fun openMapRoot(bundle: Bundle) {
        mapFragment.arguments = bundle
        clearUntilMap()
        if (mapFragment.isDetached) {
            attachMap()
        } else {
            fragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
                    .addToBackStack(MAP_ROOT_TAG)
                    .replace(container, mapFragment)
                    .commit()
        }
    }

    private fun clearUntilMap() {
        // clears back-stack until map
        fragmentManager.popBackStackImmediate(MAP_ROOT_TAG, 0)
    }

    private fun attachMap() {
        fragmentManager.beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
                .attach(mapFragment)
                .commit()
    }

    companion object {
        private const val MAP_ROOT_TAG = "map_root"
    }

}