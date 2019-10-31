package pl.reconizer.unfold.presentation.splash

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import pl.reconizer.unfold.domain.repositories.IAuthenticationRepository
import pl.reconizer.unfold.presentation.common.errorshandlers.ErrorsHandler
import pl.reconizer.unfold.presentation.common.rx.CallbackWrapper
import pl.reconizer.unfold.presentation.mvp.BasePresenter
import java.lang.ref.WeakReference

class SplashPresenter(
        private val backgroundScheduler: Scheduler,
        private val mainScheduler: Scheduler,
        private val authenticationRepository: IAuthenticationRepository,
        private val errorsHandler: ErrorsHandler
) : BasePresenter<ISplashView>() {

    private val readyToUserCheckSubject = PublishSubject.create<Boolean>()

    override fun subscribe(view: ISplashView) {
        super.subscribe(view)
        errorsHandler.view = WeakReference(view)
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
                        .subscribeWith(object : CallbackWrapper<Boolean>(errorsHandler) {
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