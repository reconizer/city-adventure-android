package pl.reconizer.unfold.presentation.navigation.keys

import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import pl.reconizer.unfold.presentation.authentication.resetpassword.secondstep.ResetPasswordSecondStepFragment

@Parcelize
class ResetPasswordSecondStepKey : BaseKey() {

    override fun createFragment(): Fragment {
        return ResetPasswordSecondStepFragment()
    }

}