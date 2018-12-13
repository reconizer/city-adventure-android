package pl.reconizer.cityadventure.presentation.adventure.journal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_adventure_journal.*
import pl.reconizer.cityadventure.R
import pl.reconizer.cityadventure.di.Injector
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.entities.AdventureStartPoint
import pl.reconizer.cityadventure.presentation.adventure.journal.clues.CluesPagesAdapter
import pl.reconizer.cityadventure.presentation.adventure.journal.clues.ViewPagerStack
import pl.reconizer.cityadventure.presentation.common.BaseFragment
import javax.inject.Inject

class JournalFragment : BaseFragment(), IJournalView {

    val adventure by lazy { arguments?.get(ADVENTURE_PARAM) as Adventure? }
    val adventureStartPoint by lazy { arguments?.get(ADVENTURE_START_POINT_PARAM) as AdventureStartPoint? }

    @Inject
    lateinit var presenter: JournalPresenter

    val cluesPagesAdapter = CluesPagesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.buildJournalComponent(adventure!!, adventureStartPoint!!).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_adventure_journal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        journalAdventureDescriptionView.rateable = false
        journalAdventureDescriptionView.isCompleted = adventure?.completed ?: false

        Picasso.get()
                .load(R.drawable.journal_background)
                .into(backgroundImage)

        adventureStartPoint?.let {
            journalAdventureDescriptionView.adventureStartPoint = it
        }

        journalTabs.tabChangeListener = {
            updateTabsVisibility(it)
        }
        updateTabsVisibility(journalTabs.activeTab)

        journalPageDescriptionView.turnable = false
        journalProgressViewPager.adapter = cluesPagesAdapter
        journalProgressViewPager.setPageTransformer(false, ViewPagerStack())
    }

    override fun onStart() {
        super.onStart()
        presenter.subscribe(this)
        presenter.fetchClues()
    }

    override fun onStop() {
        super.onStop()
        presenter.unsubscribe()
    }

    override fun showClues() {
        cluesPagesAdapter.clues = presenter.clues
        cluesPagesAdapter.notifyDataSetChanged()
    }

    private fun updateTabsVisibility(currentTab: JournalTabsView.Tabs) {
        when(currentTab){
            JournalTabsView.Tabs.PROGRESS -> {
                journalPageDescriptionView.isGone = true
                journalProgressViewPager.isGone = false
            }
            JournalTabsView.Tabs.DESCRIPTION -> {
                journalPageDescriptionView.isGone = false
                journalProgressViewPager.isGone = true
            }
        }
    }

    companion object {
        const val ADVENTURE_PARAM = "adventure"
        const val ADVENTURE_START_POINT_PARAM = "adventure_start_point"

        fun newInstance(adventure: Adventure, adventureStartPoint: AdventureStartPoint): JournalFragment {
            return JournalFragment().apply {
                arguments = bundleOf(
                        ADVENTURE_PARAM to adventure,
                        ADVENTURE_START_POINT_PARAM to adventureStartPoint
                )
            }
        }
    }
}