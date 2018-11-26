package pl.reconizer.cityadventure.presentation.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class Navigator(
        @IdRes private val container: Int,
        private val fragmentManager: FragmentManager
) : INavigator {

    override fun open(fragment: Fragment) {
        clearBackStack()
        goTo(fragment)
    }

    override fun goTo(fragment: Fragment, transitionElement: SharedTransitionElement?) {
        fragmentManager.beginTransaction().apply {
            if (transitionElement != null) {
                addSharedElement(transitionElement.view, transitionElement.transitionName)
            }
        }
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(container, fragment)
                .addToBackStack(fragment.toString())
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
                .addToBackStack(fragment.toString())
                .commit()
    }

    override fun goBack() {
        if (!isRoot()) {
            fragmentManager.popBackStackImmediate()
        }
    }

    override fun goBackToRoot() {
        clearBackStack(leave = 1)
    }

    override fun isRoot(): Boolean {
        return fragmentManager.backStackEntryCount <= 1
    }

    private fun clearBackStack(leave: Int = 0) {
        val backStackCount = fragmentManager.backStackEntryCount
        for (i in 0 until backStackCount - leave) {
            val backStackId = fragmentManager.getBackStackEntryAt(i).id
            fragmentManager.popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

}