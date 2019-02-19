package pl.reconizer.unfold.presentation.authentication.resetpassword.firststep

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_reset_password_first_step.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.di.Injector
import pl.reconizer.unfold.presentation.common.BaseFragment
import pl.reconizer.unfold.presentation.navigation.keys.ResetPasswordSecondStepKey
import javax.inject.Inject

class ResetPasswordFirstStepFragment : BaseFragment(), IResetPasswordFirstStepView {

    @Inject
    lateinit var presenter: ResetPasswordFirstStepPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.buildResetPasswordFirstStepComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reset_password_first_step, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sendCodeButton.setOnClickListener {
            presenter.sendCode(emailInput.text)
        }

        secondStepButton.setOnClickListener {
            navigator.goTo(ResetPasswordSecondStepKey())
        }

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
        Injector.clearResetPasswordFirstStepComponent()
    }

    override fun codeSent() {
        navigator.goTo(ResetPasswordSecondStepKey())
    }

}
