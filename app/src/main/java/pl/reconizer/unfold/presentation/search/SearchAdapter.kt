package pl.reconizer.unfold.presentation.search

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import pl.reconizer.unfold.domain.entities.Position
import pl.reconizer.unfold.presentation.search.adventures.AdventuresFragmentPage
import pl.reconizer.unfold.presentation.search.creators.CreatorsFragmentPage

class SearchAdapter(fm: FragmentManager, private val userPosition: Position) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            AdventuresFragmentPage.newInstance(userPosition)
        } else {
            CreatorsFragmentPage.newInstance(userPosition)
        }
    }

    override fun getCount(): Int {
        return 2
    }
}