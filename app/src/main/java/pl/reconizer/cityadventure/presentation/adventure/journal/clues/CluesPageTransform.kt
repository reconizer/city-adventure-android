package pl.reconizer.cityadventure.presentation.adventure.journal.clues

import android.view.View
import androidx.viewpager.widget.ViewPager

class ViewPagerStack : ViewPager.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        if (position >= -1 && position <= 0) {
            page.translationZ = 10f
            page.translationX = 0f


        } else {
            page.translationZ = 0f
            page.translationX = -page.width * position
        }
    }
}