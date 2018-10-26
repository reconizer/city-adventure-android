package pl.reconizer.cityadventure.presentation.authentication.login

import io.reactivex.Scheduler
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.domain.usecases.authentication.SignIn
import pl.reconizer.cityadventure.presentation.common.rx.CompletableCallbackWrapper
import pl.reconizer.cityadventure.presentation.errorhandlers.ErrorHandler
import pl.reconizer.cityadventure.presentation.mvp.BasePresenter
import java.lang.ref.WeakReference

class LoginPresenter(
        private val mainScheduler: Scheduler,
        private val signIn: SignIn,
        private val errorHandler: ErrorHandler<Error>
) : BasePresenter<ILoginView>() {

    override fun subscribe(view: ILoginView) {
        super.subscribe(view)
        errorHandler.view = WeakReference(view)
    }

    fun login(form: Form) {
        if (form.isValid()) {
            disposables.add(
                signIn(form.email, form.password)
                        .observeOn(mainScheduler)
                        .subscribeWith(object : CompletableCallbackWrapper<Error>(errorHandler) {
                            override fun onComplete() {
                                view?.successfulSignIn()
                            }
                        })
            )
        } else {
            // show errors
        }
    }
}