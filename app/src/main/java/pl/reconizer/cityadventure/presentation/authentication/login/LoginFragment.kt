package pl.reconizer.cityadventure.presentation.authentication.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.zhuinden.simplestack.StateChange
import kotlinx.android.synthetic.main.fragment_login.*
import pl.reconizer.cityadventure.R
import pl.reconizer.cityadventure.di.Injector
import pl.reconizer.cityadventure.presentation.common.BaseFragment
import pl.reconizer.cityadventure.presentation.navigation.keys.MapKey
import pl.reconizer.cityadventure.presentation.navigation.keys.ResetPasswordFirstStepKey
import javax.inject.Inject

class LoginFragment : BaseFragment(), ILoginView {

    @Inject
    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.buildLoginComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        submit.setOnClickListener {
            presenter.login(Form(
                    emailInput.text,
                    passwordInput.text
            ))
        }

        resetPasswordButton.setOnClickListener { navigator.goTo(ResetPasswordFirstStepKey()) }

        closeButton.setOnClickListener { navigator.goBack() }
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        Injector.clearLoginComponent()
    }

    override fun successfulSignIn() {
        Toast.makeText(context, "Logged in", Toast.LENGTH_LONG).show()
        navigator.setHistory(
                mutableListOf(MapKey.Builder.buildAdventuresMapKey()),
                StateChange.REPLACE
        )
    }

    companion object {
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }

}
