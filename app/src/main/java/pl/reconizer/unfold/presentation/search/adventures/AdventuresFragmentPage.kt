package pl.reconizer.unfold.presentation.search.adventures

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import kotlinx.android.synthetic.main.fragment_search_adventures_page.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.di.Injector
import pl.reconizer.unfold.domain.entities.Adventure
import pl.reconizer.unfold.domain.entities.DifficultyLevel
import pl.reconizer.unfold.domain.entities.Position
import pl.reconizer.unfold.presentation.common.BaseFragment
import pl.reconizer.unfold.presentation.common.recyclerview.EndlessRecyclerViewScrollListener
import pl.reconizer.unfold.presentation.common.recyclerview.ItemOffsetDecorator
import javax.inject.Inject

class AdventuresFragmentPage : BaseFragment(), IFilteredAdventuresView {

    @Inject
    lateinit var presenter: AdventuresPresenter

    @Inject
    lateinit var adapter: AdventuresAdapter

    private lateinit var endlessRecyclerViewScrollListener: EndlessRecyclerViewScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.buildSearchAdventuresComponent(
                arguments?.get(POSITION_PARAM) as Position? ?: throw IllegalArgumentException("User's")
        ).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_adventures_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager = LinearLayoutManager(context)
        endlessRecyclerViewScrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                if (presenter.hasGotMorePages) presenter.fetchNextPage()
            }
        }

        recyclerView.apply {
            if (itemDecorationCount == 0) {
                addItemDecoration(ItemOffsetDecorator(
                        context,
                        R.dimen.space_1x,
                        ItemOffsetDecorator.OFFSET_BOTTOM
                ))
            }
            layoutManager = linearLayoutManager
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            this.adapter = this@AdventuresFragmentPage.adapter
            addOnScrollListener(endlessRecyclerViewScrollListener)
        }

        sortButton.onSelectionListener = { selectedOrder ->
            selectedOrder?.let {
                presenter.updateSortType(it)
            }
        }

        sortButton.currentlySelectedItem?.let {
            presenter.sortType = it
        }

        filtersButton.setOnClickListener {
            AdventureFiltersDialog.newInstance(
                    AdventureFilters(
                            true,
                            0.5f,
                            true,
                            DifficultyLevel.MEDIUM
                    )
            ).show(childFragmentManager, "filters_dialog")
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe(this)
        if (presenter.items.isEmpty()) presenter.fetchFirstPage()
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }

    override fun showFirstPage(collection: List<Adventure>) {
        endlessRecyclerViewScrollListener.resetState()
        adapter.clearItems()
        adapter.addItems(collection)
    }

    override fun showNextPage(collection: List<Adventure>) {
        adapter.addItems(collection)
    }

    override fun refresh() {

    }

    override fun showListLoader() {
        showLoader()
    }

    override fun hideListLoader() {
        hideLoader()
    }

    companion object {
        const val POSITION_PARAM = "position"

        fun newInstance(position: Position): AdventuresFragmentPage {
            return AdventuresFragmentPage().apply {
                arguments = bundleOf(
                        POSITION_PARAM to position
                )
            }
        }
    }

}