package pl.reconizer.cityadventure.presentation.gallery

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class PhotoViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    /*
     * https://github.com/chrisbanes/PhotoView#issues-with-viewgroups
     */
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        try {
            return super.onInterceptTouchEvent(ev)
        } catch (e: IllegalArgumentException) {
            return false
        }

    }

}