package pl.reconizer.unfold.presentation.creatorprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_creator_profile.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.di.Injector
import pl.reconizer.unfold.domain.entities.Adventure
import pl.reconizer.unfold.presentation.common.BaseFragment
import pl.reconizer.unfold.presentation.common.recyclerview.EndlessRecyclerViewScrollListener
import pl.reconizer.unfold.presentation.common.recyclerview.ItemOffsetDecorator
import pl.reconizer.unfold.presentation.creatorprofile.adventures.CreatorAdventuresAdapter
import javax.inject.Inject

class CreatorProfileFragment : BaseFragment(), ICreatorProfileView {

    @Inject
    lateinit var presenter: CreatorProfilePresenter

    @Inject
    lateinit var adapter: CreatorAdventuresAdapter

    private lateinit var endlessRecyclerViewScrollListener: EndlessRecyclerViewScrollListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.buildCreatorProfileComponent(
                arguments?.get(CREATOR_ID_PARAM) as String? ?: throw IllegalStateException("Creator id is required.")
        ).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_creator_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        closeButton.setOnClickListener { navigator.goBack() }

        favoriteButton.setOnClickListener { presenter.toggleFollow(favoriteButton.isChecked) }

        val linearLayoutManager = LinearLayoutManager(context)
        endlessRecyclerViewScrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                if (presenter.hasGotMorePages) presenter.fetchNextPage()
            }

        }

        adventuresRecyclerView.apply {
            if (itemDecorationCount == 0) {
                addItemDecoration(ItemOffsetDecorator(
                        context,
                        R.dimen.frame_offset_with_thickness,
                        ItemOffsetDecorator.OFFSET_BOTTOM
                ))
            }
            layoutManager = linearLayoutManager
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            this.adapter = this@CreatorProfileFragment.adapter
            addOnScrollListener(endlessRecyclerViewScrollListener)
        }

    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe(this)
        if (presenter.profile == null) {
            presenter.fetchProfile()
        }
        if (presenter.items.isEmpty()) presenter.fetchFirstPage()
        showProfile()
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        Injector.clearCreatorProfileComponent()
    }

    override fun showProfile() {
        creatorName.text =  presenter.profile?.name
        description.text = presenter.profile?.description
        favoritesCounter.text = presenter.profile?.followersCount.toString()

        // TODO when api will be updated, change it to actual field
        favoriteButton.isChecked = (presenter.profile?.followersCount ?: 0) > 0

        Picasso.get()
                .load(presenter.profile?.logo)
                .into(logo)
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
        const val CREATOR_ID_PARAM = "creator_id"
    }

}