package pl.reconizer.unfold.presentation.common

import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import pl.reconizer.unfold.R

open class BaseChildFragment : BaseFragment() {

    open val customTransitionDuration: Long
        get() = resources.getInteger(R.integer.transitionDuration).toLong()

    // This is a workaround. When transitioning from a fragment which contains view pager with child fragments
    // a current child fragment disappears before main transition is finished.
    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return if (!enter && parentFragment != null) {
            AlphaAnimation(1f, 0f).apply {
                duration = customTransitionDuration
            }
        } else {
            super.onCreateAnimation(transit, enter, nextAnim)
        }
    }

}