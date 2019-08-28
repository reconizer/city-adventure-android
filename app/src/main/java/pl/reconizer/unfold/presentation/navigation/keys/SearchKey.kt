package pl.reconizer.unfold.presentation.navigation.keys

import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import pl.reconizer.unfold.presentation.search.SearchFragment

@Parcelize
class SearchKey() : BaseKey() {

    override fun createFragment(): Fragment {
        return SearchFragment()
    }

}