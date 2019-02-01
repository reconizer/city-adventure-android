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
            val previousState = stateChange.getPreviousState<BaseKey>()
            val newState = stateChange.getNewState<BaseKey>()
            val customAnimationSet =  when(stateChange.direction) {
                StateChange.FORWARD -> {
                    stateChange.topNewState<BaseKey>().customAnimations(stateChange.direction)
                }
                StateChange.BACKWARD -> {
                    stateChange.topPreviousState<BaseKey>()?.customAnimations(stateChange.direction) ?: BaseKey.DEFAULT_ANIMATION_SET
                }
                StateChange.REPLACE -> {
                    stateChange.topNewState<BaseKey>().customAnimations(stateChange.direction)
                }
                else -> throw IllegalArgumentException("Invalid state change direction")
            }
            setCustomAnimations(customAnimationSet.enter, customAnimationSet.exit, customAnimationSet.popEnter, customAnimationSet.popExit)
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