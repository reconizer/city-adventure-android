package pl.reconizer.unfold.presentation.gallery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import pl.reconizer.unfold.R

class GalleryAdapter(private val imageLoader: IImageLoader, private val context: Context?, private val images: List<String>) : PagerAdapter() {

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context)
                .inflate(R.layout.view_gallery_item, container, false) as ViewGroup
        imageLoader.loadInto(images[position], view.findViewById(R.id.imageView) as ImageView)

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }
}