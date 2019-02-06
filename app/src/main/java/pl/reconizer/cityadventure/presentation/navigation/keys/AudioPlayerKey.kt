package pl.reconizer.cityadventure.presentation.navigation.keys

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import pl.reconizer.cityadventure.presentation.customviews.AudioPlayerFragment
import pl.reconizer.cityadventure.presentation.customviews.AudioPlayerFragment.Companion.AUDIO_URL_PARAM
import pl.reconizer.cityadventure.presentation.navigation.AnimationSet

@Parcelize
class AudioPlayerKey(
        val url: String
) : BaseKey(
        bundleOf(
                AUDIO_URL_PARAM to url
        )
) {

    override fun customAnimations(changeDirection: Int): AnimationSet {
        return DEFAULT_ANIMATION_SET
    }

    override fun createFragment(): Fragment {
        return AudioPlayerFragment()
    }

}