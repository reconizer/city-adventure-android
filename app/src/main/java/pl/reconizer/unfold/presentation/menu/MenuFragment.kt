package pl.reconizer.unfold.presentation.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.zhuinden.simplestack.StateChange
import kotlinx.android.synthetic.main.fragment_menu.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.di.Injector
import pl.reconizer.unfold.presentation.common.BaseFragment
import pl.reconizer.unfold.presentation.navigation.keys.AuthenticationStartKey
import javax.inject.Inject

class MenuFragment : BaseFragment(), IMenuView {

    @Inject
    lateinit var presenter: MenuPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.buildMenuComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        closeButton.setOnClickListener { navigator.goBack() }

        logoutMenuItem.setOnClickListener { presenter.logout() }
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe(this)
        presenter.fetchProfile()
        showProfile()
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        Injector.clearMenuComponent()
    }

    override fun showProfile() {
        presenter.profile?.let {
            usernameTextView.text = it.nick

            Picasso.get()
                    .load(it.avatarUrl)
                    .into(avatar)
        }
    }

    override fun successfulLogout() {
        navigator.setHistory(
                mutableListOf(AuthenticationStartKey()),
                StateChange.REPLACE
        )
    }

}