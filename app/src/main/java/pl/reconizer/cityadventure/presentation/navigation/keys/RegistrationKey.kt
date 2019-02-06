package pl.reconizer.cityadventure.presentation.navigation.keys

import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import pl.reconizer.cityadventure.presentation.authentication.registration.RegistrationFragment

@Parcelize
class RegistrationKey : BaseKey() {

    override fun createFragment(): Fragment {
        return RegistrationFragment()
    }

}