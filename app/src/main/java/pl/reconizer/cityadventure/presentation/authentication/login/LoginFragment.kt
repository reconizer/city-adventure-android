package pl.reconizer.cityadventure.presentation.authentication.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pl.reconizer.cityadventure.CityAdventureApp
import pl.reconizer.cityadventure.R
import javax.inject.Inject

class LoginFragment : Fragment(), ILoginView {

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
    }

    override fun successfulSignIn() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
