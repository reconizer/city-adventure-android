package pl.reconizer.cityadventure.presentation.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class Navigator(
        @IdRes private val container: Int,
        private val fragmentManager: FragmentManager
) : INavigator {

    override fun goTo(fragment: Fragment) {
        fragmentManager.beginTransaction()
                .replace(container, fragment)
//                .setCustomAnimations(R.anim.slide_in_left,
//                        R.anim.slide_out_right,
//                        R.anim.slide_in_right,
//                        R.anim.slide_out_left)
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

    private fun clearBackStack(leave: Int) {
        val backStackCount = fragmentManager.backStackEntryCount
        for (i in 0 until backStackCount - leave) {
            val backStackId = fragmentManager.getBackStackEntryAt(i).id
            fragmentManager.popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

}