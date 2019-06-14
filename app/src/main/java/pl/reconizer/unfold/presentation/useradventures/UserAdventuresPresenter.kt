package pl.reconizer.unfold.presentation.useradventures

import io.reactivex.Scheduler
import io.reactivex.Single
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.domain.entities.ICollectionContainer
import pl.reconizer.unfold.domain.entities.UserAdventure
import pl.reconizer.unfold.domain.entities.UserProfile
import pl.reconizer.unfold.domain.repositories.IUserRepository
import pl.reconizer.unfold.presentation.common.rx.SingleCallbackWrapper
import pl.reconizer.unfold.presentation.errorhandlers.ErrorsHandler
import pl.reconizer.unfold.presentation.mvp.PaginatedDataPresenter
import java.lang.ref.WeakReference

class UserAdventuresPresenter(
        backgroundScheduler: Scheduler,
        mainScheduler: Scheduler,
        private val userRepository: IUserRepository,
        errorsHandler: ErrorsHandler<Error>,
        val adventuresType: UserAdventuresType
) : PaginatedDataPresenter<UserAdventure, IUserAdventuresView>(
        backgroundScheduler,
        mainScheduler,
        errorsHandler
) {

    override fun subscribe(view: IUserAdventuresView) {
        super.subscribe(view)
        errorsHandler.view = WeakReference(view)
    }

    override fun load(page: Int): Single<ICollectionContainer<UserAdventure>> {
        return when (adventuresType) {
            UserAdventuresType.STARTED -> userRepository.getStartedAdventures(page)
            UserAdventuresType.COMPLETED -> userRepository.getCompletedAdventures(page)
            UserAdventuresType.PURCHASED -> userRepository.getPurchasedAdventures(page)
        }
    }

}