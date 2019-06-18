package pl.reconizer.unfold.presentation.adventure.journal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import kotlinx.android.synthetic.main.fragment_adventure_journal.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.common.extensions.openInBrowser
import pl.reconizer.unfold.di.Injector
import pl.reconizer.unfold.domain.entities.MapAdventure
import pl.reconizer.unfold.domain.entities.AdventureStartPoint
import pl.reconizer.unfold.domain.entities.ClueType
import pl.reconizer.unfold.presentation.adventure.journal.clues.CluesPagesAdapter
import pl.reconizer.unfold.presentation.adventure.journal.clues.ViewPagerStack
import pl.reconizer.unfold.presentation.common.BaseFragment
import pl.reconizer.unfold.presentation.customviews.dialogs.PrettyDialog
import pl.reconizer.unfold.presentation.navigation.keys.AudioPlayerKey
import pl.reconizer.unfold.presentation.navigation.keys.GalleryKey
import pl.reconizer.unfold.presentation.navigation.keys.MapKey
import javax.inject.Inject

class JournalFragment : BaseFragment(), IJournalView {

    val adventure by lazy { arguments?.get(ADVENTURE_PARAM) as MapAdventure? }
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

        adventureStartPoint?.let {
            journalAdventureDescriptionView.adventureStartPoint = it
            journalAdventureDescriptionView.galleryImageClickListener = {imageIndex ->
                navigator.goTo(GalleryKey(
                        it.gallery,
                        imageIndex
                ))
            }
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
            clueClickListener = { clue ->
                when (clue.type) {
                    ClueType.IMAGE -> {
                        clue.originalResourceUrl?.let {
                            navigator.goTo(GalleryKey(
                                    listOf(it),
                                    0
                            ))
                        }

                    }
                    ClueType.AUDIO -> {
                        clue.originalResourceUrl?.let {
                            navigator.goTo(AudioPlayerKey(it))
                        }
                    }
                    ClueType.URL -> {
                        clue.originalResourceUrl?.let {
                            openInBrowser(it, resources.getString(R.string.journal_cannot_open_url))
                        }
                    }
                }

            }
            pointClickListener = { pointId ->
                adventure?.let {
                    navigator.goTo(MapKey.Builder.buildAdventureMapKey(
                             adventure = it,
                             adventurePointId = pointId
                    ))
                }
            }
        }

        exitButton.setOnClickListener {
            showExitAdventureDialog()
        }

        goToMapButton.setOnClickListener { _ ->
            adventure?.let {
                navigator.goTo(MapKey.Builder.buildAdventureMapKey(
                        it
                ))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.subscribe(this)
        view?.postDelayed({
            presenter.fetchClues()
        }, resources.getInteger(R.integer.transitionDuration).toLong())
    }

    override fun onStop() {
        super.onStop()
        presenter.unsubscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        Injector.clearJournalComponent()
    }

    override fun goBack(): Boolean {
        showExitAdventureDialog()
        return true
    }

    override fun showClues() {
        cluesPagesAdapter.points = presenter.points
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

    private fun showExitAdventureDialog() {
        PrettyDialog().apply {
            headerText = this@JournalFragment.resources.getString(R.string.journal_exit)
            contentText = this@JournalFragment.resources.getString(R.string.journal_exit_info)
            firstButtonText = this@JournalFragment.resources.getString(R.string.common_yes)
            secondButtonText = this@JournalFragment.resources.getString(R.string.common_no)
            show(this@JournalFragment.childFragmentManager, "alert")
        }.apply {
            firstButtonClickListener = {
                dismiss()
                navigator.jumpToRoot()
            }
            secondButtonClickListener = { dismiss() }
        }
    }

    companion object {
        const val ADVENTURE_PARAM = "adventure"
        const val ADVENTURE_START_POINT_PARAM = "adventure_start_point"

        fun newInstance(adventure: MapAdventure, adventureStartPoint: AdventureStartPoint): JournalFragment {
            return JournalFragment().apply {
                arguments = bundleOf(
                        ADVENTURE_PARAM to adventure,
                        ADVENTURE_START_POINT_PARAM to adventureStartPoint
                )
            }
        }
    }
}