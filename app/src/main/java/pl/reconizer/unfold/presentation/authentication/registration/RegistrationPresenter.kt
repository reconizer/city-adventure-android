package pl.reconizer.unfold.presentation.authentication.registration

import io.reactivex.Scheduler
import pl.reconizer.unfold.domain.usecases.authentication.SignUp
import pl.reconizer.unfold.presentation.common.errorshandlers.ErrorsHandler
import pl.reconizer.unfold.presentation.common.rx.CompletableCallbackWrapper
import pl.reconizer.unfold.presentation.mvp.BasePresenter
import java.lang.ref.WeakReference

class RegistrationPresenter(
        private val mainScheduler: Scheduler,
        private val signUp: SignUp,
        private val errorsHandler: ErrorsHandler
) : BasePresenter<IRegistrationView>() {

    override fun subscribe(view: IRegistrationView) {
        super.subscribe(view)
        errorsHandler.view = WeakReference(view)
    }

    fun register(form: Form) {
        if (form.isValid()) {
            disposables.add(
                signUp(form.email, form.username, form.password)
                        .observeOn(mainScheduler)
                        .doOnSubscribe { view?.showLoader() }
                        .doFinally { view?.hideLoader() }
                        .subscribeWith(object : CompletableCallbackWrapper(errorsHandler) {
                            override fun onComplete() {
                                view?.successfulSignUp()
                            }
                        })
            )
        } else {
            // show errors
        }
    }
}