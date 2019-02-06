package pl.reconizer.cityadventure.presentation.navigation.keys

import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import pl.reconizer.cityadventure.presentation.menu.MenuFragment

@Parcelize
class MenuKey() : BaseKey() {

    override fun createFragment(): Fragment {
        return MenuFragment()
    }

}