package pl.reconizer.unfold.presentation.search.creators

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
import kotlinx.android.synthetic.main.fragment_search_creators_page.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.di.Injector
import pl.reconizer.unfold.domain.entities.Creator
import pl.reconizer.unfold.domain.entities.Position
import pl.reconizer.unfold.presentation.common.BaseChildFragment
import pl.reconizer.unfold.presentation.common.recyclerview.EndlessRecyclerViewScrollListener
import pl.reconizer.unfold.presentation.common.recyclerview.ItemOffsetDecorator
import pl.reconizer.unfold.presentation.navigation.keys.CreatorProfileKey
import javax.inject.Inject

class CreatorsFragmentPage : BaseChildFragment(), IFilteredCreatorsView {

    @Inject
    lateinit var presenter: CreatorsPresenter

    @Inject
    lateinit var adapter: CreatorsAdapter

    private lateinit var endlessRecyclerViewScrollListener: EndlessRecyclerViewScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.buildSearchCreatorsComponent(
                arguments?.get(POSITION_PARAM) as Position? ?: throw IllegalArgumentException("User's position is required")
        ).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_creators_page, container, false)
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
            this.adapter = this@CreatorsFragmentPage.adapter
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

        closeByCheckBox.setOnCheckedChangeListener { _, isChecked ->
            presenter.updateDisplayCloseByCreatorsFlag(isChecked)
        }
        presenter.displayCloseByCreators = closeByCheckBox.isChecked

        searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                presenter.updateNameFilter(s.toString())
            }
        })

        searchInput.setText(presenter.nameFilter)

        adapter.onItemClickListener = { clickedCreator ->
            navigator.goTo(CreatorProfileKey(clickedCreator.id))
        }

    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe(this)
        if (presenter.items.isEmpty()) {
            view?.postDelayed({
                presenter.fetchFirstPage()
            }, 200L)
        }
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }

    override fun showFirstPage(collection: List<Creator>) {
        endlessRecyclerViewScrollListener.resetState()
        adapter.clearItems()
        adapter.addItems(collection)
    }

    override fun showNextPage(collection: List<Creator>) {
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

        fun newInstance(position: Position): CreatorsFragmentPage {
            return CreatorsFragmentPage().apply {
                arguments = bundleOf(
                        POSITION_PARAM to position
                )
            }
        }
    }

}