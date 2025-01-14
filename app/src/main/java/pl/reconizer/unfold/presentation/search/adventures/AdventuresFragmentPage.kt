package pl.reconizer.unfold.presentation.search.adventures

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import pl.reconizer.unfold.domain.entities.Position
import pl.reconizer.unfold.presentation.common.BaseChildFragment
import pl.reconizer.unfold.presentation.common.recyclerview.EndlessRecyclerViewScrollListener
import pl.reconizer.unfold.presentation.common.recyclerview.ItemOffsetDecorator
import javax.inject.Inject

class AdventuresFragmentPage : BaseChildFragment(), IFilteredAdventuresView {

    @Inject
    lateinit var presenter: AdventuresPresenter

    @Inject
    lateinit var adapter: AdventuresAdapter

    private lateinit var endlessRecyclerViewScrollListener: EndlessRecyclerViewScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.buildSearchAdventuresComponent(
                arguments?.get(POSITION_PARAM) as Position? ?: throw IllegalArgumentException("User's position is required")
        ).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_adventures_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchInput.setText(presenter.filters.name)

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
                    presenter.filters
            ).apply {
                onApplyListener = { filters ->
                    presenter.filters = filters
                    showActiveFilters()
                    presenter.fetchFirstPage()
                }
            }.show(childFragmentManager, "filters_dialog")
        }

        searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                presenter.updateNameFilter(s.toString())
            }
        })

        showActiveFilters()
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe(this)
        if (presenter.items.isEmpty() || presenter.hasFiltersChanged) {
            view?.postDelayed({
                presenter.fetchFirstPage()
            }, 200L)
        }
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

    private fun showActiveFilters() {
        if (presenter.filters.activeFiltersCount > 0) {
            filtersButton.text = "${resources.getString(R.string.common_filter)} (${presenter.filters.activeFiltersCount})"
        } else {
            filtersButton.setText(R.string.common_filter)
        }
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