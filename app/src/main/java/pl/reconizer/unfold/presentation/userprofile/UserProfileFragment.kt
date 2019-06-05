package pl.reconizer.unfold.presentation.userprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_user_profile.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.di.Injector
import pl.reconizer.unfold.presentation.common.BaseFragment
import pl.reconizer.unfold.presentation.common.recyclerview.ItemOffsetDecorator
import pl.reconizer.unfold.presentation.navigation.keys.EditUserProfileKey
import pl.reconizer.unfold.presentation.userprofile.adventures.UserAdventuresAdapter
import javax.inject.Inject

class UserProfileFragment : BaseFragment(), IUserProfileView {

    @Inject
    lateinit var presenter: UserProfilePresenter

    @Inject
    lateinit var adapter: UserAdventuresAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.buildUserProfileComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editButton.setOnClickListener { navigator.goTo(EditUserProfileKey()) }

        closeButton.setOnClickListener { navigator.goBack() }

        adventuresRecyclerView.apply {
            if (itemDecorationCount == 0) {
                addItemDecoration(ItemOffsetDecorator(
                        context,
                        R.dimen.frame_offset_with_thickness,
                        ItemOffsetDecorator.OFFSET_BOTTOM or ItemOffsetDecorator.OFFSET_LEFT or ItemOffsetDecorator.OFFSET_RIGHT
                ))
            }
            layoutManager = LinearLayoutManager(context)
            this.adapter = this@UserProfileFragment.adapter
        }

    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe(this)
        presenter.fetchProfile()
        presenter.fetchAdventures()
        showProfile()
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        Injector.clearUserProfileComponent()
    }

    override fun showProfile() {
        presenter.profile?.let {
            usernameTextView.text = it.nick

            Picasso.get()
                    .load(it.avatarUrl)
                    .into(avatar)
        }
    }

    override fun showAdventures() {
        adapter.submitList(presenter.adventures)
    }

}