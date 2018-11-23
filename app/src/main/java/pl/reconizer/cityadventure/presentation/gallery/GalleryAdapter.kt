package pl.reconizer.cityadventure.presentation.gallery

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class GalleryAdapter(fragmentManager: FragmentManager, private val images: List<String>, private val startIndex: Int) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return GalleryItemFragment.newInstance(images[position], "galleryItem_$startIndex")
    }

    override fun getCount(): Int {
        return images.size
    }

}