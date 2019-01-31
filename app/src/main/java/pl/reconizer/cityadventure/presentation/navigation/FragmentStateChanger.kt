package pl.reconizer.cityadventure.presentation.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.zhuinden.simplestack.StateChange
import pl.reconizer.cityadventure.R

class FragmentStateChanger(
        private val fragmentManager: FragmentManager,
        private val containerId: Int
) {
    fun handleStateChange(stateChange: StateChange) {
        val fragmentTransaction = fragmentManager.beginTransaction().apply {
            when (stateChange.direction) {
                StateChange.FORWARD -> {
                    setCustomAnimations(R.anim.push_from_right, R.anim.push_out_left, R.anim.push_from_left, R.anim.push_out_right)
                }
                StateChange.BACKWARD -> {
                    setCustomAnimations(R.anim.push_from_left, R.anim.push_out_right, R.anim.push_from_left, R.anim.push_out_right)
                }
                StateChange.REPLACE -> {
                    setCustomAnimations(R.anim.push_from_right, R.anim.push_out_left, R.anim.push_from_left, R.anim.push_out_right)
                }
            }
            val previousState = stateChange.getPreviousState<BaseKey>()
            val newState = stateChange.getNewState<BaseKey>()
            for (oldKey in previousState) {
                val fragment = fragmentManager.findFragmentByTag(oldKey.fragmentTag)
                if (fragment != null) {
                    if ((oldKey.isIdentifiedByTag && newState.find { it.fragmentTag == oldKey.fragmentTag } == null) || (!oldKey.isIdentifiedByTag && !newState.contains(oldKey))) {
                        remove(fragment)
                    } else if (!fragment.isDetached) {
                        detach(fragment)
                    }
                }
            }
            for (newKey in newState) {
                var fragment: Fragment? = fragmentManager.findFragmentByTag(newKey.fragmentTag)
                if (newKey == stateChange.topNewState<Any>()) {
                    if (fragment != null) {
                        if (newKey.hasNewArguments) {
                            fragment.arguments = newKey.arguments
                        }
                        if (fragment.isDetached) {
                            attach(fragment)
                        }
                    } else {
                        fragment = newKey.newFragment()
                        add(containerId, fragment, newKey.fragmentTag)
                    }
                } else {
                    if (fragment != null && !fragment.isDetached) {
                        detach(fragment)
                    }
                }
            }
        }
        fragmentTransaction.commitAllowingStateLoss()
    }
}