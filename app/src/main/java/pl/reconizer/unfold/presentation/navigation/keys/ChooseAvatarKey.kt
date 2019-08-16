package pl.reconizer.unfold.presentation.navigation.keys

import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import pl.reconizer.unfold.presentation.avatars.ChooseAvatarFragment

@Parcelize
class ChooseAvatarKey : BaseKey() {

    override fun createFragment(): Fragment {
        return ChooseAvatarFragment()
    }

}