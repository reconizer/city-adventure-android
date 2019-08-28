package pl.reconizer.unfold.presentation.navigation.keys

import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import pl.reconizer.unfold.presentation.userprofile.edit.EditUserProfileFragment

@Parcelize
class EditUserProfileKey() : BaseKey() {

    override fun createFragment(): Fragment {
        return EditUserProfileFragment()
    }

}