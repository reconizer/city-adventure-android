package pl.reconizer.unfold.presentation.useradventures

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.fragment_user_adventures.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.presentation.common.BaseFragment

class UserAdventuresFragment : BaseFragment() {

    lateinit var adapter: UserAdventuresPageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = UserAdventuresPageAdapter(childFragmentManager)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_adventures, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adventuresPager.adapter = adapter

        adventuresPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                val type = when(position) {
                    1 -> UserAdventuresType.COMPLETED
                    2 -> UserAdventuresType.PURCHASED
                    else -> UserAdventuresType.STARTED
                }
                adventuresFilterTabs.currentTab = type
            }
        })

        adventuresFilterTabs.onTabChangeListener = {
            val position = when(it) {
                UserAdventuresType.STARTED -> 0
                UserAdventuresType.COMPLETED -> 1
                UserAdventuresType.PURCHASED -> 2
            }
            adventuresPager.currentItem = position
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adventuresPager.clearOnPageChangeListeners()
    }

}