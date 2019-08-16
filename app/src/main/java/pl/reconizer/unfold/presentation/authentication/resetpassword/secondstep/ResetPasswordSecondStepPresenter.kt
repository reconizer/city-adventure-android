package pl.reconizer.unfold.presentation.authentication.resetpassword.secondstep

import io.reactivex.Scheduler
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.domain.repositories.IUserRepository
import pl.reconizer.unfold.presentation.common.rx.CompletableCallbackWrapper
import pl.reconizer.unfold.presentation.errorhandlers.ErrorsHandler
import pl.reconizer.unfold.presentation.mvp.BasePresenter
import java.lang.ref.WeakReference

class ResetPasswordSecondStepPresenter(
        private val backgroundScheduler: Scheduler,
        private val mainScheduler: Scheduler,
        private val userRepository: IUserRepository,
        private val errorsHandler: ErrorsHandler<Error>
) : BasePresenter<IResetPasswordSecondStepView>() {

    override fun subscribe(view: IResetPasswordSecondStepView) {
        super.subscribe(view)
        errorsHandler.view = WeakReference(view)
    }

    fun resetPassword(form: Form) {
        if (form.isValid()) {
            disposables.add(
                userRepository.resetPassword(form.code, form.password)
                        .subscribeOn(backgroundScheduler)
                        .observeOn(mainScheduler)
                        .doOnSubscribe { view?.showLoader() }
                        .doFinally { view?.hideLoader() }
                        .subscribeWith(object : CompletableCallbackWrapper<Error>(errorsHandler) {
                            override fun onComplete() {
                                view?.successfulPasswordReset()
                            }
                        })
            )
        } else {
            // show errors
        }
    }
}