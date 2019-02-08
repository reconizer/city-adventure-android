package pl.reconizer.cityadventure.presentation.navigation.keys

import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import pl.reconizer.cityadventure.presentation.navigation.AnimationSet
import pl.reconizer.cityadventure.presentation.splash.SplashFragment

@Parcelize
class SplashKey : BaseKey() {

    override fun createFragment(): Fragment {
        return SplashFragment()
    }

    override fun customAnimations(changeDirection: Int): AnimationSet? {
        return null
    }

}