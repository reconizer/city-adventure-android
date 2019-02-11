package pl.reconizer.cityadventure.presentation.splash

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.domain.repositories.IAuthenticationRepository
import pl.reconizer.cityadventure.presentation.common.rx.CallbackWrapper
import pl.reconizer.cityadventure.presentation.common.rx.SingleCallbackWrapper
import pl.reconizer.cityadventure.presentation.errorhandlers.ErrorHandler
import pl.reconizer.cityadventure.presentation.mvp.BasePresenter
import java.lang.ref.WeakReference

class SplashPresenter(
        private val backgroundScheduler: Scheduler,
        private val mainScheduler: Scheduler,
        private val authenticationRepository: IAuthenticationRepository,
        private val errorHandler: ErrorHandler<Error>
) : BasePresenter<ISplashView>() {

    private val readyToUserCheckSubject = PublishSubject.create<Boolean>()

    override fun subscribe(view: ISplashView) {
        super.subscribe(view)
        errorHandler.view = WeakReference(view)
    }

    fun checkUser() {
        disposables.add(
                Observable.zip(
                        readyToUserCheckSubject,
                        authenticationRepository.getToken().map { it.isNotBlank() }.toObservable(),
                        BiFunction { ready: Boolean, isAuthenticated: Boolean -> isAuthenticated }
                )
                        .subscribeOn(backgroundScheduler)
                        .observeOn(mainScheduler)
                        .subscribeWith(object : CallbackWrapper<Boolean, Error>(errorHandler) {
                            override fun onComplete() {
                            }

                            override fun onNext(t: Boolean) {
                                if(t) {
                                    view?.authenticatedUser()
                                } else {
                                    view?.unauthenticatedUser()
                                }
                            }
                        })
        )
    }

    fun notifyViewAboutUserState() {
        readyToUserCheckSubject.onNext(true)
    }
}