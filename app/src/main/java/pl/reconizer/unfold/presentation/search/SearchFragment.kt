package pl.reconizer.unfold.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.fragment_search.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.domain.entities.Position
import pl.reconizer.unfold.presentation.common.BaseFragment

class SearchFragment : BaseFragment() {

    lateinit var adapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = SearchAdapter(childFragmentManager, Position(
                53.0138,
                18.5984
        ))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        closeButton.setOnClickListener { navigator.goBack() }

        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                when(position) {
                    0 -> tabsContainer.check(R.id.adventuresTab)
                    else -> tabsContainer.check(R.id.creatorsTab)
                }
            }
        })

        tabsContainer.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.adventuresTab -> viewPager.currentItem = 0
                R.id.creatorsTab -> viewPager.currentItem = 1
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewPager.clearOnPageChangeListeners()
    }

}