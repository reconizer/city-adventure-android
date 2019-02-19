package pl.reconizer.unfold.presentation.authentication.resetpassword.firststep

import io.reactivex.Scheduler
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.domain.repositories.IUserRepository
import pl.reconizer.unfold.presentation.common.rx.CompletableCallbackWrapper
import pl.reconizer.unfold.presentation.errorhandlers.ErrorHandler
import pl.reconizer.unfold.presentation.mvp.BasePresenter
import java.lang.ref.WeakReference

class ResetPasswordFirstStepPresenter(
        private val backgroundScheduler: Scheduler,
        private val mainScheduler: Scheduler,
        private val userRepository: IUserRepository,
        private val errorHandler: ErrorHandler<Error>
) : BasePresenter<IResetPasswordFirstStepView>() {

    override fun subscribe(view: IResetPasswordFirstStepView) {
        super.subscribe(view)
        errorHandler.view = WeakReference(view)
    }

    fun sendCode(email: String) {
        if (email.isNotBlank()) {
            disposables.add(
                userRepository.sendResetPasswordCode(email)
                        .subscribeOn(backgroundScheduler)
                        .observeOn(mainScheduler)
                        .doOnSubscribe { view?.showLoader() }
                        .doFinally { view?.hideLoader() }
                        .subscribeWith(object : CompletableCallbackWrapper<Error>(errorHandler) {
                            override fun onComplete() {
                                view?.codeSent()
                            }
                        })
            )
        } else {
            // show errors
        }
    }
}