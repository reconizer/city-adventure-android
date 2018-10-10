package pl.reconizer.cityadventure.presentation.authentication.login

import io.reactivex.Scheduler
import pl.reconizer.cityadventure.domain.usecases.authentication.SignIn
import pl.reconizer.cityadventure.presentation.mvp.BasePresenter

class LoginPresenter(
        private val mainScheduler: Scheduler,
        private val signIn: SignIn
) : BasePresenter<ILoginView>() {

    fun login(form: Form) {
        if (form.isValid()) {
            disposables.add(
                signIn(form.email!!, form.password!!)
                        .observeOn(mainScheduler)
                        .subscribe(
                                { view?.successfulSignIn() },
                                { /* show errors*/ }
                        )
            )
        } else {
            // show errors
        }
    }
}