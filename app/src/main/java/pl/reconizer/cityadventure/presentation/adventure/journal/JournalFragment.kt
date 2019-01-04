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
import pl.reconizer.cityadventure.presentation.customviews.PrettyDialog
import javax.inject.Inject

class JournalFragment : BaseFragment(), IJournalView {

    val adventure by lazy { arguments?.get(ADVENTURE_PARAM) as Adventure? }
    val adventureStartPoint by lazy { arguments?.get(ADVENTURE_START_POINT_PARAM) as AdventureStartPoint? }

    @Inject
    lateinit var presenter: JournalPresenter

    private val cluesPagesAdapter = CluesPagesAdapter()

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

        journalAdventureDescriptionView.apply {
            rateable = false
            isCompleted = this@JournalFragment.adventure?.completed ?: false
        }
        journalPageDescriptionView.apply {
            turnableRight = false
            turnableLeft = false
        }

        Picasso.get()
                .load(R.drawable.journal_background)
                .into(backgroundImage)

        Picasso.get()
                .load(R.drawable.journal_content_background_cover)
                .into(journalCover)

        Picasso.get()
                .load(R.drawable.journal_content_background)
                .into(journalContentBackground)

        adventureStartPoint?.let {
            journalAdventureDescriptionView.adventureStartPoint = it
        }

        journalTabs.tabChangeListener = {
            updateTabsVisibility(it)
        }
        updateTabsVisibility(journalTabs.activeTab)

        journalProgressViewPager.adapter = cluesPagesAdapter
        journalProgressViewPager.setPageTransformer(false, ViewPagerStack())

        cluesPagesAdapter.apply {
            turnLeftListener = { currentPageNumber ->
                journalProgressViewPager.currentItem = currentPageNumber - 1
            }
            turnRightListener = { currentPageNumber ->
                journalProgressViewPager.currentItem = currentPageNumber + 1
            }
        }

        exitButton.setOnClickListener {
            PrettyDialog().apply {
                headerText = this@JournalFragment.resources.getString(R.string.adventure_journal_exit)
                contentText = this@JournalFragment.resources.getString(R.string.journal_exit_info)
                firstButtonText = this@JournalFragment.resources.getString(R.string.common_yes)
                secondButtonText = this@JournalFragment.resources.getString(R.string.common_no)
                show(this@JournalFragment.childFragmentManager, "alert")
            }
        }
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