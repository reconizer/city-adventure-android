package pl.reconizer.unfold.presentation.navigation.keys

import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import pl.reconizer.unfold.presentation.userprofile.UserProfileFragment

@Parcelize
class UserProfileKey() : BaseKey() {

    override fun createFragment(): Fragment {
        return UserProfileFragment()
    }

}