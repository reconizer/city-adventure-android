package pl.reconizer.unfold.presentation.userprofile

import io.reactivex.Scheduler
import io.reactivex.Single
import pl.reconizer.unfold.domain.entities.ICollectionContainer
import pl.reconizer.unfold.domain.entities.UserAdventure
import pl.reconizer.unfold.domain.entities.UserProfile
import pl.reconizer.unfold.domain.repositories.IUserRepository
import pl.reconizer.unfold.presentation.common.errorshandlers.ErrorsHandler
import pl.reconizer.unfold.presentation.common.rx.SingleCallbackWrapper
import pl.reconizer.unfold.presentation.mvp.PaginatedDataPresenter
import java.lang.ref.WeakReference

class UserProfilePresenter(
        backgroundScheduler: Scheduler,
        mainScheduler: Scheduler,
        private val userRepository: IUserRepository,
        errorsHandler: ErrorsHandler
) : PaginatedDataPresenter<UserAdventure, IUserProfileView>(
        backgroundScheduler,
        mainScheduler,
        errorsHandler
) {

    var profile: UserProfile? = null
        private set

    override fun subscribe(view: IUserProfileView) {
        super.subscribe(view)
        errorsHandler.view = WeakReference(view)
    }

    fun fetchProfile() {
        disposables.add(
                userRepository.getProfile()
                        .subscribeOn(backgroundScheduler)
                        .observeOn(mainScheduler)
                        .subscribeWith(object : SingleCallbackWrapper<UserProfile>(errorsHandler) {
                            override fun onSuccess(t: UserProfile) {
                                profile = t
                                view?.showProfile()
                            }
                        })
        )
    }

    override fun load(page: Int): Single<ICollectionContainer<UserAdventure>> {
        return userRepository.getCompletedAdventures(page)
    }

}