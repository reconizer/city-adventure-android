package pl.reconizer.cityadventure.presentation.navigation.keys

import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import pl.reconizer.cityadventure.presentation.authentication.login.LoginFragment

@Parcelize
class LoginKey : BaseKey() {

    override fun createFragment(): Fragment {
        return LoginFragment()
    }

}