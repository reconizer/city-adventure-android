package pl.reconizer.unfold.presentation.creatorprofile

import io.reactivex.Scheduler
import io.reactivex.Single
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.domain.entities.ICollectionContainer
import pl.reconizer.unfold.domain.entities.UserAdventure
import pl.reconizer.unfold.domain.entities.UserProfile
import pl.reconizer.unfold.domain.repositories.ICreatorRepository
import pl.reconizer.unfold.domain.repositories.IUserRepository
import pl.reconizer.unfold.presentation.common.rx.SingleCallbackWrapper
import pl.reconizer.unfold.presentation.errorhandlers.ErrorsHandler
import pl.reconizer.unfold.presentation.mvp.BasePresenter
import pl.reconizer.unfold.presentation.mvp.PaginatedDataPresenter
import java.lang.ref.WeakReference

class CreatorProfilePresenter(
        private val backgroundScheduler: Scheduler,
        private val mainScheduler: Scheduler,
        private val creatorRepository: ICreatorRepository,
        private val errorsHandler: ErrorsHandler<Error>
) : BasePresenter<ICreatorProfileView>() {

    var profile: UserProfile? = null
        private set

    override fun subscribe(view: ICreatorProfileView) {
        super.subscribe(view)
        errorsHandler.view = WeakReference(view)
    }

}