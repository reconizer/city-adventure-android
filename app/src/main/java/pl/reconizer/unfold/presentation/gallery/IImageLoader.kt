package pl.reconizer.unfold.presentation.gallery

import android.widget.ImageView

interface IImageLoader {

    fun loadInto(url: String, view: ImageView)

}