package pl.reconizer.cityadventure.presentation.navigation

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import pl.reconizer.cityadventure.R
import pl.reconizer.cityadventure.presentation.gallery.GalleryFragment

@Parcelize
class GalleryKey(
        val images: List<String>,
        val index: Int = 0
) : BaseKey(
        bundleOf(
                GalleryFragment.IMAGES_PARAM to images,
                GalleryFragment.START_INDEX_PARAM to index
        )
) {

    override fun customAnimations(changeDirection: Int): AnimationSet {
        return DEFAULT_ANIMATION_SET
    }

    override fun createFragment(): Fragment {
        return GalleryFragment()
    }

}