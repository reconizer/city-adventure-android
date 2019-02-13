package pl.reconizer.unfold.presentation.authentication.resetpassword.secondstep

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhuinden.simplestack.StateChange
import kotlinx.android.synthetic.main.fragment_reset_password_second_step.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.di.Injector
import pl.reconizer.unfold.presentation.common.BaseFragment
import pl.reconizer.unfold.presentation.navigation.keys.AuthenticationStartKey
import javax.inject.Inject

class ResetPasswordSecondStepFragment : BaseFragment(), IResetPasswordSecondStepView {

    @Inject
    lateinit var presenter: ResetPasswordSecondStepPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.buildResetPasswordSecondStepComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reset_password_second_step, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        submit.setOnClickListener {
            presenter.resetPassword(Form(
                    codeInput.text,
                    passwordInput.text,
                    passwordConfirmationInput.text
            ))
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
        Injector.clearResetPasswordSecondStepComponent()
    }

    override fun successfulPasswordReset() {
        navigator.setHistory(
                mutableListOf(AuthenticationStartKey()),
                StateChange.REPLACE
        )
    }

}
