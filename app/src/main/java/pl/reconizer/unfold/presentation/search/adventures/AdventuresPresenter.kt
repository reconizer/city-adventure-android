package pl.reconizer.unfold.presentation.search.adventures

import io.reactivex.Scheduler
import io.reactivex.Single
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.domain.entities.*
import pl.reconizer.unfold.domain.repositories.IAdventureRepository
import pl.reconizer.unfold.presentation.errorhandlers.ErrorsHandler
import pl.reconizer.unfold.presentation.mvp.PaginatedDataPresenter
import java.lang.ref.WeakReference

class AdventuresPresenter(
        backgroundScheduler: Scheduler,
        mainScheduler: Scheduler,
        private val adventuresRepository: IAdventureRepository,
        errorsHandler: ErrorsHandler<Error>,
        private val position: Position
) : PaginatedDataPresenter<Adventure, IFilteredAdventuresView>(
        backgroundScheduler,
        mainScheduler,
        errorsHandler
) {

    var sortType: AdventuresSort = AdventuresSort.RATING

    override fun subscribe(view: IFilteredAdventuresView) {
        super.subscribe(view)
        errorsHandler.view = WeakReference(view)
    }

    override fun load(page: Int): Single<ICollectionContainer<Adventure>> {
        return adventuresRepository.searchAdventures(
                page,
                position,
                sortType
        )
    }

    fun updateSortType(newSortType: AdventuresSort) {
        sortType = newSortType
        fetchFirstPage()
    }

}