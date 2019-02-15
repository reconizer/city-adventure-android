package pl.reconizer.unfold.presentation.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.fragment_authentication.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.presentation.common.BaseFragment
import pl.reconizer.unfold.presentation.navigation.keys.LoginKey
import pl.reconizer.unfold.presentation.navigation.keys.RegistrationKey

class AuthenticationStartFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_authentication, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signinButton.setOnClickListener { navigator.goTo(LoginKey()) }

        signupButton.setOnClickListener { navigator.goTo(RegistrationKey()) }

        backgroundImageLight.startAnimation(
                AnimationUtils.loadAnimation(context, R.anim.torch_light)
        )
    }

    override fun onResume() {
        super.onResume()
        view?.postDelayed({
            showLoader()

        }, 1000)
    }
}