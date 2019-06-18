package pl.reconizer.unfold.presentation.navigation

import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import pl.reconizer.unfold.domain.entities.MapAdventure
import pl.reconizer.unfold.presentation.map.MapMode
import pl.reconizer.unfold.presentation.map.game.GameMapFragment
import pl.reconizer.unfold.presentation.map.game.GameMapFragment.Companion.ADVENTURE_PARAM
import pl.reconizer.unfold.presentation.map.game.GameMapFragment.Companion.ADVENTURE_POINT_ID_PARAM
import pl.reconizer.unfold.presentation.map.game.GameMapFragment.Companion.MAP_MODE_PARAM

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

    override fun showAdventuresMap() {
        mapFragment.arguments = bundleOf(
                MAP_MODE_PARAM to MapMode.ADVENTURES
        )
        clearUntilMap()
        goToMap()
    }

    override fun showAdventureMap(adventure: MapAdventure, adventurePointId: String?) {
        mapFragment.arguments = bundleOf(
                MAP_MODE_PARAM to MapMode.STARTED_ADVENTURE,
                ADVENTURE_PARAM to adventure,
                ADVENTURE_POINT_ID_PARAM to adventurePointId
        )
        goToMap()
    }

//    override fun showMap(bundle: Bundle) {
//        mapFragment.arguments = bundle
//        goTo(mapFragment)
////        if (mapFragment.isDetached) {
////            attachMap()
////        } else {
////            openMapRoot(bundle)
////        }
//
//    }
//
//    override fun leaveMap() {
//        if (mapFragment.isAdded) {
////            fragmentManager.beginTransaction()
////                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
////                    .detach(mapFragment)
////                    .commit()
//        }
//    }
//
//    override fun openMapRoot(bundle: Bundle) {
//        mapFragment.arguments = bundle
//        clearUntilMap()
//        if (mapFragment.isDetached) {
//            attachMap()
//        } else {
//            fragmentManager.beginTransaction()
//                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
//                    .addToBackStack(MAP_ROOT_TAG)
//                    .replace(container, mapFragment)
//                    .commit()
//        }
//    }

    private fun clearUntilMap() {
        // clears back-stack until map
//        while (fragmentManager.backStackEntryCount > 1) {
//            fragmentManager.popBackStackImmediate()
//        }

        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    private fun attachMap() {
        fragmentManager.beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
                .attach(mapFragment)
                //.addToBackStack(null)
                .commit()
    }

    private fun goToMap() {
        fragmentManager.beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
                .apply {
                    if (mapFragment.mapMode == MapMode.ADVENTURES) {
                        addToBackStack(MAP_ROOT_TAG)
                        replace(container, mapFragment)
                    } else {
                        addToBackStack(MAP_ROOT_TAG)
                        replace(container, mapFragment)
//                        add(container, mapFragment)
                    }
                }
                .commit()
    }

    companion object {
        private const val MAP_ROOT_TAG = "map_root"
    }

}