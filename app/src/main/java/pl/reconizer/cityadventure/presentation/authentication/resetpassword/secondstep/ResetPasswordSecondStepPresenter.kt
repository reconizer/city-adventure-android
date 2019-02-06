package pl.reconizer.cityadventure.presentation.authentication.resetpassword.secondstep

import io.reactivex.Scheduler
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.domain.repositories.IUserRepository
import pl.reconizer.cityadventure.presentation.common.rx.CompletableCallbackWrapper
import pl.reconizer.cityadventure.presentation.errorhandlers.ErrorHandler
import pl.reconizer.cityadventure.presentation.mvp.BasePresenter
import java.lang.ref.WeakReference

class ResetPasswordSecondStepPresenter(
        private val backgroundScheduler: Scheduler,
        private val mainScheduler: Scheduler,
        private val userRepository: IUserRepository,
        private val errorHandler: ErrorHandler<Error>
) : BasePresenter<IResetPasswordSecondStepView>() {

    override fun subscribe(view: IResetPasswordSecondStepView) {
        super.subscribe(view)
        errorHandler.view = WeakReference(view)
    }

    fun resetPassword(form: Form) {
        if (form.isValid()) {
            disposables.add(
                userRepository.resetPassword(form.code, form.password)
                        .subscribeOn(backgroundScheduler)
                        .observeOn(mainScheduler)
                        .subscribeWith(object : CompletableCallbackWrapper<Error>(errorHandler) {
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