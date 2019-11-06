package pl.reconizer.unfold.presentation.navigation.keys

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import pl.reconizer.unfold.presentation.avatars.ChooseAvatarFragment

@Parcelize
data class ChooseAvatarKey(
        val resultsForKey: BaseKey
) : BaseKey(bundleOf(
        ChooseAvatarFragment.PARENT_KEY_PARAM to resultsForKey
)) {

    override fun createFragment(): Fragment {
        return ChooseAvatarFragment()
    }

}