package pl.reconizer.cityadventure.presentation.menu

import io.reactivex.Scheduler
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.domain.repositories.IUserRepository
import pl.reconizer.cityadventure.domain.usecases.authentication.Logout
import pl.reconizer.cityadventure.presentation.common.rx.CompletableCallbackWrapper
import pl.reconizer.cityadventure.presentation.errorhandlers.ErrorHandler
import pl.reconizer.cityadventure.presentation.mvp.BasePresenter
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
                        .subscribeWith(object : CompletableCallbackWrapper<Error>(errorHandler) {
                            override fun onComplete() {
                                view?.successfulLogout()
                            }

                        })
        )
    }

}