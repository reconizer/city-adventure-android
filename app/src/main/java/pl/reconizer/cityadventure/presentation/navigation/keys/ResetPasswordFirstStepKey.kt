package pl.reconizer.cityadventure.presentation.navigation.keys

import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import pl.reconizer.cityadventure.presentation.authentication.resetpassword.firststep.ResetPasswordFirstStepFragment

@Parcelize
class ResetPasswordFirstStepKey : BaseKey() {

    override fun createFragment(): Fragment {
        return ResetPasswordFirstStepFragment()
    }

}