package pl.reconizer.cityadventure.presentation.authentication.registration

import io.reactivex.Scheduler
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.domain.usecases.authentication.SignUp
import pl.reconizer.cityadventure.presentation.common.rx.CompletableCallbackWrapper
import pl.reconizer.cityadventure.presentation.errorhandlers.ErrorHandler
import pl.reconizer.cityadventure.presentation.mvp.BasePresenter
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
                signUp(form.email, form.password)
                        .observeOn(mainScheduler)
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