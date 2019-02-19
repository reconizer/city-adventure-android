package pl.reconizer.unfold.presentation.adventure.journal.clues

import android.text.SpannableString
import android.text.Spanned
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.view_journal_clues_page.view.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.domain.entities.AdventurePointWithClues
import pl.reconizer.unfold.domain.entities.Clue
import pl.reconizer.unfold.presentation.common.recyclerview.ItemOffsetDecorator

class CluesPagesAdapter : PagerAdapter() {

    var turnLeftListener: ((currentPageNumber: Int) -> Unit)? = null
    var turnRightListener: ((currentPageNumber: Int) -> Unit)? = null

    var clueClickListener: ((clue: Clue) -> Unit)? = null
    var pointClickListener: ((pointId: String) -> Unit)? = null

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

        preparePointHeader(view, points[position])

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

    private fun preparePointHeader(view: View, point: AdventurePointWithClues) {
        view.pointHeader.text = SpannableString(point.discoveryDateString).apply {
            setSpan(UnderlineSpan(), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        view.pointHeaderContainer.setOnClickListener {
            pointClickListener?.invoke(point.id)
        }
    }

}