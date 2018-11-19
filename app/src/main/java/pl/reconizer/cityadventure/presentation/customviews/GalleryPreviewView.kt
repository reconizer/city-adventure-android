package pl.reconizer.cityadventure.presentation.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_gallery_preview.view.*
import pl.reconizer.cityadventure.R

class GalleryPreviewView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var images: List<String> = emptyList()

    var thumbClickListener: ((idx: Int, view: View) -> Unit)? = null

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.view_gallery_preview, this, true)

        firstImage.setOnClickListener { thumbClickListener?.invoke(0, it) }
        secondImage.setOnClickListener { thumbClickListener?.invoke(1, it) }
        thirdImage.setOnClickListener { thumbClickListener?.invoke(2, it) }
        moreImagesContainer.setOnClickListener { thumbClickListener?.invoke(3, it) }
    }

    fun setImages(newImages: List<String>) {
        images = newImages
        updateGrid()
    }

    private fun updateGrid() {
        firstImage.isGone = images.isEmpty()
        secondImage.isGone = images.size < 2
        thirdImage.isGone = images.size < 3
        moreImagesContainer.isGone = images.size < 4
        firstImageShadow.isGone = firstImage.isGone
        secondImageShadow.isGone = secondImage.isGone
        thirdImageShadow.isGone = thirdImage.isGone
        fourthImageShadow.isGone = moreImagesContainer.isGone
        fourthImage.isGone = images.size < 4
        if (images.size > 4) {
            imageOverlay.isGone = false
            galleryImageCount.isGone = false
            galleryImageCount.text = "+${images.size - 3}"
        } else {
            imageOverlay.isGone = false
            galleryImageCount.isGone = false
        }
        listOf(firstImage, secondImage, thirdImage, fourthImage).forEachIndexed { index, imageView ->
            Picasso.get()
                    .load(images.getOrNull(index))
                    .into(imageView)
        }
    }
}