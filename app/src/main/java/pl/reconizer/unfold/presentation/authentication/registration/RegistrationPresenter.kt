package pl.reconizer.unfold.presentation.authentication.registration

import io.reactivex.Scheduler
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.domain.usecases.authentication.SignUp
import pl.reconizer.unfold.presentation.common.rx.CompletableCallbackWrapper
import pl.reconizer.unfold.presentation.errorhandlers.ErrorHandler
import pl.reconizer.unfold.presentation.mvp.BasePresenter
import java.lang.ref.WeakReference

class RegistrationPresenter(
        private val mainScheduler: Scheduler,
        private val signUp: SignUp,
        private val errorHandler: ErrorHandler<Error>
) : BasePresenter<IRegistrationView>() {

    override fun subscribe(view: IRegistrationView) {
        super.subscribe(view)
        errorHandler.view = WeakReference(view)
    }

    fun register(form: Form) {
        if (form.isValid()) {
            disposables.add(
                signUp(form.email, form.username, form.password)
                        .observeOn(mainScheduler)
                        .doOnSubscribe { view?.showLoader() }
                        .doFinally { view?.hideLoader() }
                        .subscribeWith(object : CompletableCallbackWrapper<Error>(errorHandler) {
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