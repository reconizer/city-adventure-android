package pl.reconizer.unfold.presentation.avatars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import kotlinx.android.synthetic.main.fragment_choose_avatar.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.di.Injector
import pl.reconizer.unfold.presentation.common.BaseFragment
import pl.reconizer.unfold.presentation.common.recyclerview.ItemOffsetDecorator
import javax.inject.Inject

class ChooseAvatarFragment : BaseFragment(), IChooseAvatarView {

    @Inject
    lateinit var presenter: ChooseAvatarPresenter

    @Inject
    lateinit var adapter: AvatarsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.buildChooseAvatarComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_choose_avatar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gridLayoutManager = GridLayoutManager(context, 2)
        avatarsRecyclerView.apply {
            if (itemDecorationCount == 0) {
                addItemDecoration(ItemOffsetDecorator(
                        context,
                        R.dimen.space_1x,
                        ItemOffsetDecorator.OFFSET_BOTTOM or ItemOffsetDecorator.OFFSET_INSIDE
                ))
            }
            layoutManager = gridLayoutManager
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            this.adapter = this@ChooseAvatarFragment.adapter
        }

        closeButton.setOnClickListener { navigator.goBack() }
        goBackButton.setOnClickListener { navigator.goBack() }

        saveButton.setOnClickListener {
            adapter.selectedAvatar?.let {
                presenter.changeAvatar(it.id)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe(this)
        presenter.fetchAvatars()
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        Injector.clearChooseAvatarComponent()
    }

    override fun showAvatars() {
        adapter.items = presenter.avatars
        adapter.notifyDataSetChanged()
    }

    override fun avatarUpdated() {
        navigator.goBack()
    }

}