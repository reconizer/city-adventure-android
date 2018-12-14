package pl.reconizer.cityadventure.presentation.adventure.journal.clues

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.view_journal_clues_page.view.*
import pl.reconizer.cityadventure.R
import pl.reconizer.cityadventure.domain.entities.Clue
import pl.reconizer.cityadventure.presentation.common.recyclerview.ItemOffsetDecorator

class CluesPagesAdapter : PagerAdapter() {

    var turnLeftListener: ((currentPageNumber: Int) -> Unit)? = null
    var turnRightListener: ((currentPageNumber: Int) -> Unit)? = null

    private var cluesByPoint: Map<String, List<Clue>> = emptyMap()
    private var points: List<String> = emptyList()

    var clues: List<Clue> = emptyList()
        set(value) {
            field = value
            cluesByPoint = value.groupBy { it.pointId }
            points = cluesByPoint.keys.toList()
        }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun getCount(): Int {
        return points.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context)
                .inflate(R.layout.view_journal_clues_page, container, false) as ViewGroup
        val cluesAdapter = CluesAdapter()
        view.cluesRecyclerView.apply {
            if (itemDecorationCount == 0) addItemDecoration(ItemOffsetDecorator(container.context, R.dimen.journalClueOffset))
            layoutManager = LinearLayoutManager(context)
            adapter = cluesAdapter
        }
        cluesAdapter.clues = cluesByPoint[points[position]] ?: emptyList()
        cluesAdapter.notifyDataSetChanged()
        container.addView(view)
        view.journalPageView.turnableLeft = position != 0
        view.journalPageView.turnableRight = position < points.size - 1
        if (position < points.size) {
            view.journalPageView.turnRightListener = { turnRightListener?.invoke(position) }
        }
        if (position > 0) {
            view.journalPageView.turnLeftListener = { turnLeftListener?.invoke(position) }
        }
        return view
    }

    override fun finishUpdate(container: ViewGroup) {
        super.finishUpdate(container)
    }



    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }

}