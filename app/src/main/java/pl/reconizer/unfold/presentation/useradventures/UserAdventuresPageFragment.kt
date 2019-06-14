package pl.reconizer.unfold.presentation.useradventures

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import kotlinx.android.synthetic.main.fragment_user_adventures_page.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.di.Injector
import pl.reconizer.unfold.domain.entities.UserAdventure
import pl.reconizer.unfold.presentation.common.BaseFragment
import pl.reconizer.unfold.presentation.common.recyclerview.EndlessRecyclerViewScrollListener
import pl.reconizer.unfold.presentation.common.recyclerview.ItemOffsetDecorator
import javax.inject.Inject

class UserAdventuresPageFragment : BaseFragment(), IUserAdventuresView {

    @Inject
    lateinit var presenter: UserAdventuresPresenter

    @Inject
    lateinit var adapter: UserAdventuresAdapter

    private lateinit var endlessRecyclerViewScrollListener: EndlessRecyclerViewScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.buildUserAdventuresComponent(
                arguments?.get(ADVENTURES_TYPE_PARAM) as UserAdventuresType? ?: UserAdventuresType.COMPLETED
        ).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_adventures_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gridLayoutManager = GridLayoutManager(context, 2)
        endlessRecyclerViewScrollListener = object : EndlessRecyclerViewScrollListener(gridLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                if (presenter.hasGotMorePages) presenter.fetchNextPage()
            }
        }

        adventuresRecyclerView.apply {
            if (itemDecorationCount == 0) {
                addItemDecoration(ItemOffsetDecorator(
                        context,
                        R.dimen.space_1x,
                        ItemOffsetDecorator.OFFSET_BOTTOM or ItemOffsetDecorator.OFFSET_INSIDE
                ))
            }
            layoutManager = gridLayoutManager
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            this.adapter = this@UserAdventuresPageFragment.adapter
            addOnScrollListener(endlessRecyclerViewScrollListener)
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

    override fun showFirstPage(collection: List<UserAdventure>) {
        endlessRecyclerViewScrollListener.resetState()
        adapter.clearItems()
        adapter.addItems(collection)
    }

    override fun showNextPage(collection: List<UserAdventure>) {
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
        const val ADVENTURES_TYPE_PARAM = "adventures_type"

        fun newInstance(type: UserAdventuresType): UserAdventuresPageFragment {
            return UserAdventuresPageFragment().apply {
                arguments = bundleOf(
                        ADVENTURES_TYPE_PARAM to type
                )
            }
        }

    }

}