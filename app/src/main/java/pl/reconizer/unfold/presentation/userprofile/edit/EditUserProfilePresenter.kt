package pl.reconizer.unfold.presentation.userprofile.edit

import io.reactivex.Scheduler
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.domain.entities.UserProfile
import pl.reconizer.unfold.domain.entities.forms.UserProfileForm
import pl.reconizer.unfold.domain.repositories.IUserRepository
import pl.reconizer.unfold.presentation.common.rx.CompletableCallbackWrapper
import pl.reconizer.unfold.presentation.common.rx.SingleCallbackWrapper
import pl.reconizer.unfold.presentation.errorhandlers.ErrorHandler
import pl.reconizer.unfold.presentation.mvp.BasePresenter
import java.lang.ref.WeakReference

class EditUserProfilePresenter(
        private val backgroundScheduler: Scheduler,
        private val mainScheduler: Scheduler,
        private val userRepository: IUserRepository,
        private val errorHandler: ErrorHandler<Error>
) : BasePresenter<IEditUserProfileView>() {

    var profile: UserProfile? = null

    override fun subscribe(view: IEditUserProfileView) {
        super.subscribe(view)
        errorHandler.view = WeakReference(view)
    }

    fun fetchProfile() {
        disposables.add(
                userRepository.getProfile()
                        .subscribeOn(backgroundScheduler)
                        .observeOn(mainScheduler)
                        .subscribeWith(object : SingleCallbackWrapper<UserProfile, Error>(errorHandler) {
                            override fun onSuccess(t: UserProfile) {
                                profile = t
                                view?.showProfile()
                            }
                        })
        )
    }

    fun updateProfile(form: UserProfileForm) {
        if (form.isValid()) {
            disposables.add(
                    userRepository.updateProfile(form)
                            .subscribeOn(backgroundScheduler)
                            .observeOn(mainScheduler)
                            .doOnSubscribe {
                                profile = profile?.copy(nick = form.nick ?: "")
                                view?.showLoader()
                            }
                            .doFinally { view?.hideLoader() }
                            .subscribeWith(object : CompletableCallbackWrapper<Error>(errorHandler) {
                                override fun onComplete() {
                                    view?.profileUpdated()
                                }
                            })
            )
        } else {
            // TODO show error
        }
    }

}