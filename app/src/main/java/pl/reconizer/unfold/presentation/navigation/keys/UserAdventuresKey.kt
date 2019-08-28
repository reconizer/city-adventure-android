package pl.reconizer.unfold.presentation.navigation.keys

import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import pl.reconizer.unfold.presentation.useradventures.UserAdventuresFragment

@Parcelize
class UserAdventuresKey() : BaseKey() {

    override fun createFragment(): Fragment {
        return UserAdventuresFragment()
    }

}