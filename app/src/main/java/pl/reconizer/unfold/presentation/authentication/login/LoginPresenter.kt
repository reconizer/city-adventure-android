package pl.reconizer.unfold.presentation.authentication.login

import io.reactivex.Scheduler
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.domain.usecases.authentication.SignIn
import pl.reconizer.unfold.presentation.common.rx.CompletableCallbackWrapper
import pl.reconizer.unfold.presentation.errorhandlers.ErrorHandler
import pl.reconizer.unfold.presentation.mvp.BasePresenter
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