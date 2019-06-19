package pl.reconizer.unfold.presentation.search.creators

import io.reactivex.Scheduler
import io.reactivex.Single
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.domain.entities.*
import pl.reconizer.unfold.domain.repositories.ICreatorRepository
import pl.reconizer.unfold.presentation.errorhandlers.ErrorsHandler
import pl.reconizer.unfold.presentation.mvp.PaginatedDataPresenter
import java.lang.ref.WeakReference

class CreatorsPresenter(
        backgroundScheduler: Scheduler,
        mainScheduler: Scheduler,
        private val creatorRepository: ICreatorRepository,
        errorsHandler: ErrorsHandler<Error>,
        private val position: Position
) : PaginatedDataPresenter<Creator, IFilteredCreatorsView>(
        backgroundScheduler,
        mainScheduler,
        errorsHandler
) {

    override fun subscribe(view: IFilteredCreatorsView) {
        super.subscribe(view)
        errorsHandler.view = WeakReference(view)
    }

    override fun load(page: Int): Single<ICollectionContainer<Creator>> {
        return creatorRepository.search(page, position, false)
    }

}