package pl.reconizer.unfold.presentation.userprofile.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_user_profile_edit.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.di.Injector
import pl.reconizer.unfold.domain.entities.forms.UserProfileForm
import pl.reconizer.unfold.presentation.common.BaseFragment
import pl.reconizer.unfold.presentation.navigation.keys.ChooseAvatarKey
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
        goBackButton.setOnClickListener { navigator.goBack() }

        saveButton.setOnClickListener {
            presenter.updateProfile(UserProfileForm(usernameInput.text.toString(), null))
        }

        changeAvatarButton.setOnClickListener {
            navigator.goTo(ChooseAvatarKey())
        }

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
            usernameInput.setText(it.nick)

            Picasso.get()
                    .load(it.avatarUrl)
                    .into(avatar)
        }
    }

    override fun profileUpdated() {
        Toast.makeText(context, R.string.user_profile_updated, Toast.LENGTH_SHORT).show()
    }

}