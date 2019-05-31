package pl.reconizer.unfold.presentation.userprofile.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_user_profile_edit.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.di.Injector
import pl.reconizer.unfold.presentation.common.BaseFragment
import javax.inject.Inject

class EditUserProfileFragment : BaseFragment(), IEditUserProfileView {

    @Inject
    lateinit var presenter: EditUserProfilePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.buildEditUserProfileComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_profile_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        closeButton.setOnClickListener { navigator.goBack() }

    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe(this)
        if (presenter.profile == null) {
            presenter.fetchProfile()
        }
        showProfile()
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        Injector.clearEditUserProfileComponent()
    }

    override fun showProfile() {
        presenter.profile?.let {
            usernameTextView.setText(it.nick)

            Picasso.get()
                    .load(it.avatarUrl)
                    .into(avatar)
        }
    }

}