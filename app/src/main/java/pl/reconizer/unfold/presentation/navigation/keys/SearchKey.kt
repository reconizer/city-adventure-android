package pl.reconizer.unfold.presentation.navigation.keys

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import pl.reconizer.unfold.domain.entities.Position
import pl.reconizer.unfold.presentation.search.SearchFragment

@Parcelize
class SearchKey(
        val position: Position
) : BaseKey(
        bundleOf(
                SearchFragment.POSITION_PARAM to position
        )
) {

    override fun createFragment(): Fragment {
        return SearchFragment()
    }

}