package pl.reconizer.unfold.presentation.navigation.keys

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import pl.reconizer.unfold.presentation.creatorprofile.CreatorProfileFragment

@Parcelize
class CreatorProfileKey(val creatorId: String) : BaseKey(
        bundleOf(
                CreatorProfileFragment.CREATOR_ID_PARAM to creatorId
        )
) {

    override fun createFragment(): Fragment {
        return CreatorProfileFragment()
    }

}