package pl.reconizer.cityadventure.presentation.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.transition.ChangeBounds
import androidx.transition.Fade
import androidx.transition.TransitionInflater
import kotlinx.android.synthetic.main.fragment_gallery.*
import pl.reconizer.cityadventure.R
import pl.reconizer.cityadventure.presentation.common.BaseFragment

class GalleryFragment : BaseFragment() {

    val images by lazy { arguments!![IMAGES_PARAM] as List<String> }
    val startIndex by lazy { arguments!![START_INDEX_PARAM] as Int }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
        enterTransition = Fade()
        exitTransition = Fade()
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(R.transition.gallery_transition)
        sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(R.transition.gallery_transition)

//        setEnterSharedElementCallback(object : SharedElementCallback() {
//            override fun onMapSharedElements(names: MutableList<String>?, sharedElements: MutableMap<String, View>?) {
//                val currentPagerView = galleryViewPager.adapter?.instantiateItem(galleryViewPager, startIndex) as View
//
//                // Map the first shared element name to the child ImageView.
//                sharedElements!![names!!.first()] = currentPagerView.findViewById(R.id.image)
//            }
//        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        galleryViewPager.adapter = GalleryAdapter(childFragmentManager, images, startIndex)
        galleryViewPager.currentItem = startIndex
        closeButton.setOnClickListener { navigator.goBack() }
    }

    companion object {
        const val IMAGES_PARAM = "images"
        const val START_INDEX_PARAM = "start_index"

        fun newInstance(images: List<String>, index: Int = 0): GalleryFragment {
            return GalleryFragment().apply {
                arguments = bundleOf(
                        IMAGES_PARAM to images,
                        START_INDEX_PARAM to index
                )
            }
        }
    }
}