package pl.reconizer.unfold.presentation.authentication.login

import io.reactivex.Scheduler
import pl.reconizer.unfold.domain.usecases.authentication.SignIn
import pl.reconizer.unfold.presentation.common.errorshandlers.ErrorsHandler
import pl.reconizer.unfold.presentation.common.rx.CompletableCallbackWrapper
import pl.reconizer.unfold.presentation.mvp.BasePresenter
import java.lang.ref.WeakReference

class LoginPresenter(
        private val mainScheduler: Scheduler,
        private val signIn: SignIn,
        private val errorsHandler: ErrorsHandler
) : BasePresenter<ILoginView>() {

    override fun subscribe(view: ILoginView) {
        super.subscribe(view)
        errorsHandler.view = WeakReference(view)
    }

    fun login(form: Form) {
        if (form.isValid()) {
            disposables.add(
                signIn(form.email, form.password)
                        .observeOn(mainScheduler)
                        .doOnSubscribe { view?.showLoader() }
                        .doFinally { view?.hideLoader() }
                        .subscribeWith(object : CompletableCallbackWrapper(errorsHandler) {
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