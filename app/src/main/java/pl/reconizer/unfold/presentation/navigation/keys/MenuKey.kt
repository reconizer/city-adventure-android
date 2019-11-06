package pl.reconizer.unfold.presentation.navigation.keys

import androidx.fragment.app.Fragment
import com.zhuinden.simplestack.StateChange
import kotlinx.android.parcel.Parcelize
import pl.reconizer.unfold.R
import pl.reconizer.unfold.presentation.menu.MenuFragment
import pl.reconizer.unfold.presentation.navigation.AnimationSet

@Parcelize
class MenuKey : BaseKey() {

    override fun createFragment(): Fragment {
        return MenuFragment()
    }

    override fun customAnimations(changeDirection: Int): AnimationSet? {
        return when (changeDirection) {
            StateChange.FORWARD -> {
                AnimationSet(R.anim.push_from_left, R.anim.push_out_right, R.anim.push_from_right, R.anim.push_out_left)
            }
            StateChange.BACKWARD -> {
                AnimationSet(R.anim.push_from_right, R.anim.push_out_left, R.anim.push_from_left, R.anim.push_out_right)
            }
            StateChange.REPLACE -> {
                DEFAULT_ANIMATION_SET
            }
            else -> throw IllegalArgumentException("Invalid state change direction")
        }
    }

}