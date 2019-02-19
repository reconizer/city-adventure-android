package pl.reconizer.unfold.presentation.navigation.keys

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import pl.reconizer.unfold.presentation.gallery.GalleryFragment
import pl.reconizer.unfold.presentation.navigation.AnimationSet

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