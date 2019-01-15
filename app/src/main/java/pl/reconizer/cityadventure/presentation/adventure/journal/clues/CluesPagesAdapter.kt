package pl.reconizer.cityadventure.presentation.adventure.journal.clues

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.view_journal_clues_page.view.*
import pl.reconizer.cityadventure.R
import pl.reconizer.cityadventure.domain.entities.AdventurePointWithClues
import pl.reconizer.cityadventure.domain.entities.Clue
import pl.reconizer.cityadventure.presentation.common.recyclerview.ItemOffsetDecorator

class CluesPagesAdapter : PagerAdapter() {

    var turnLeftListener: ((currentPageNumber: Int) -> Unit)? = null
    var turnRightListener: ((currentPageNumber: Int) -> Unit)? = null

    var clueClickListener: ((clue: Clue) -> Unit)? = null

    var points: List<AdventurePointWithClues> = emptyList()

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
        cluesAdapter.clues = points[position].clues
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
        cluesAdapter.clueClickListener = clueClickListener
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }

}