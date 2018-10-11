package pl.reconizer.cityadventure.presentation.authentication.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_login.*
import pl.reconizer.cityadventure.CityAdventureApp
import pl.reconizer.cityadventure.R
import pl.reconizer.cityadventure.presentation.common.BaseFragment
import javax.inject.Inject

class LoginFragment : BaseFragment(), ILoginView {

    @Inject
    lateinit var presenter: LoginPresenter

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        CityAdventureApp.appComponent.loginComponent(LoginModule()).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        submit.setOnClickListener {
            presenter.login(Form(
                    emailInput.text.toString(),
                    passwordInput.text.toString()
            ))
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }

    override fun successfulSignIn() {
        Toast.makeText(context, "Logged in", Toast.LENGTH_LONG).show()
    }

}
