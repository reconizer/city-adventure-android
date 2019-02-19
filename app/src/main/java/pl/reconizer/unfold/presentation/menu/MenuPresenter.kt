package pl.reconizer.unfold.presentation.menu

import io.reactivex.Scheduler
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.domain.repositories.IUserRepository
import pl.reconizer.unfold.domain.usecases.authentication.Logout
import pl.reconizer.unfold.presentation.common.rx.CompletableCallbackWrapper
import pl.reconizer.unfold.presentation.errorhandlers.ErrorHandler
import pl.reconizer.unfold.presentation.mvp.BasePresenter
import java.lang.ref.WeakReference

class MenuPresenter(
        private val backgroundScheduler: Scheduler,
        private val mainScheduler: Scheduler,
        private val userRepository: IUserRepository,
        private val logout: Logout,
        private val errorHandler: ErrorHandler<Error>
) : BasePresenter<IMenuView>() {

    override fun subscribe(view: IMenuView) {
        super.subscribe(view)
        errorHandler.view = WeakReference(view)
    }

    fun fetchProfile() {
        view?.showProfile()
    }

    fun logout () {
        disposables.add(
                logout.invoke()
                        .observeOn(mainScheduler)
                        .doOnSubscribe { view?.showLoader() }
                        .doFinally { view?.hideLoader() }
                        .subscribeWith(object : CompletableCallbackWrapper<Error>(errorHandler) {
                            override fun onComplete() {
                                view?.successfulLogout()
                            }

                        })
        )
    }

}