package pl.reconizer.unfold.presentation.navigation.keys

import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import pl.reconizer.unfold.presentation.authentication.registration.RegistrationFragment

@Parcelize
class RegistrationKey : BaseKey() {

    override fun createFragment(): Fragment {
        return RegistrationFragment()
    }

}