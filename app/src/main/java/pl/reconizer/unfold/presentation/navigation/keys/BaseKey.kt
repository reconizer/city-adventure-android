package pl.reconizer.unfold.presentation.navigation.keys

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.zhuinden.simplestack.StateChange
import pl.reconizer.unfold.R
import pl.reconizer.unfold.presentation.navigation.AnimationSet
import pl.reconizer.unfold.presentation.navigation.Key

abstract class BaseKey(
        override var arguments: Bundle? = null,
        override var hasNewArguments: Boolean = false
) : Key {

    override fun newFragment(): Fragment = createFragment().also { fragment ->
        fragment.arguments = arguments
    }

    protected abstract fun createFragment(): Fragment

    open fun customAnimations(changeDirection: Int): AnimationSet? {
        return when (changeDirection) {
            StateChange.FORWARD -> {
                AnimationSet(R.anim.push_from_right, R.anim.push_out_left, R.anim.push_from_left, R.anim.push_out_right)
            }
            StateChange.BACKWARD -> {
                AnimationSet(R.anim.push_from_left, R.anim.push_out_right, R.anim.push_from_left, R.anim.push_out_right)
            }
            StateChange.REPLACE -> {
                AnimationSet(R.anim.push_from_right, R.anim.push_out_left, R.anim.push_from_left, R.anim.push_out_right)
            }
            else -> throw IllegalArgumentException("Invalid state change direction")
        }
    }

    override fun overrideArguments(newArguments: Bundle?) {
        hasNewArguments = true
        arguments = newArguments
    }

    override val fragmentTag: String
        get() = toString()

    companion object {
        val DEFAULT_ANIMATION_SET = AnimationSet(
                R.anim.fade_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.fade_out
        )
    }
}