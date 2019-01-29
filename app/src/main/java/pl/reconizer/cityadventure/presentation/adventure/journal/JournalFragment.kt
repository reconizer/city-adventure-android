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
import pl.reconizer.cityadventure.domain.entities.ClueType
import pl.reconizer.cityadventure.presentation.adventure.journal.clues.CluesPagesAdapter
import pl.reconizer.cityadventure.presentation.adventure.journal.clues.ViewPagerStack
import pl.reconizer.cityadventure.presentation.common.BaseFragment
import pl.reconizer.cityadventure.presentation.customviews.dialogs.PrettyDialog
import pl.reconizer.cityadventure.presentation.navigation.AudioPlayerKey
import pl.reconizer.cityadventure.presentation.navigation.GalleryKey
import pl.reconizer.cityadventure.presentation.navigation.MapKey
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
                .noFade()
                .into(backgroundImage)

        Picasso.get()
                .load(R.drawable.journal_content_background_cover)
                .noFade()
                .into(journalCover)

        Picasso.get()
                .load(R.drawable.journal_content_background)
                .noFade()
                .into(journalContentBackground)

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
        presenter.fetchClues()
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