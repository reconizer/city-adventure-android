package pl.reconizer.unfold.presentation.navigation.keys

import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import pl.reconizer.unfold.presentation.authentication.AuthenticationStartFragment

@Parcelize
class AuthenticationStartKey : BaseKey() {

    override fun createFragment(): Fragment {
        return AuthenticationStartFragment()
    }

}