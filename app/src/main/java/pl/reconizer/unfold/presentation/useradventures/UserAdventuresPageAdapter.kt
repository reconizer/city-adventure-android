package pl.reconizer.unfold.presentation.useradventures

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class UserAdventuresPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        val type = when(position) {
            1 -> UserAdventuresType.COMPLETED
            2 -> UserAdventuresType.PURCHASED
            else -> UserAdventuresType.STARTED
        }
        return UserAdventuresPageFragment.newInstance(type)
    }

    override fun getCount(): Int {
        return 3
    }
}