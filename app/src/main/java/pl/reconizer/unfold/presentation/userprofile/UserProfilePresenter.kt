package pl.reconizer.unfold.presentation.userprofile

import io.reactivex.Scheduler
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.domain.entities.UserAdventure
import pl.reconizer.unfold.domain.entities.UserProfile
import pl.reconizer.unfold.domain.repositories.IUserRepository
import pl.reconizer.unfold.presentation.common.rx.SingleCallbackWrapper
import pl.reconizer.unfold.presentation.errorhandlers.ErrorHandler
import pl.reconizer.unfold.presentation.mvp.BasePresenter
import java.lang.ref.WeakReference

class UserProfilePresenter(
        private val backgroundScheduler: Scheduler,
        private val mainScheduler: Scheduler,
        private val userRepository: IUserRepository,
        private val errorHandler: ErrorHandler<Error>
) : BasePresenter<IUserProfileView>() {

    var profile: UserProfile? = null
        private set

    var adventures: List<UserAdventure> = emptyList()
        private set

    override fun subscribe(view: IUserProfileView) {
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

    fun fetchAdventures() {
        disposables.add(
                userRepository.getCompletedAdventures()
                        .subscribeOn(backgroundScheduler)
                        .observeOn(mainScheduler)
                        .subscribeWith(object : SingleCallbackWrapper<List<UserAdventure>, Error>(errorHandler) {
                            override fun onSuccess(t: List<UserAdventure>) {
                                adventures = t
                                view?.showAdventures()
                            }
                        })
        )
    }

}