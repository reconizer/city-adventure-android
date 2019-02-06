package pl.reconizer.cityadventure.presentation.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_authentication.*
import pl.reconizer.cityadventure.R
import pl.reconizer.cityadventure.presentation.common.BaseFragment
import pl.reconizer.cityadventure.presentation.navigation.keys.LoginKey
import pl.reconizer.cityadventure.presentation.navigation.keys.RegistrationKey

class AuthenticationStartFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_authentication, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signinButton.setOnClickListener { navigator.goTo(LoginKey()) }

        signupButton.setOnClickListener { navigator.goTo(RegistrationKey()) }
    }
}