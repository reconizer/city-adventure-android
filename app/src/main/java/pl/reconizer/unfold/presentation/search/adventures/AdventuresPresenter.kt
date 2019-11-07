package pl.reconizer.unfold.presentation.search.adventures

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import pl.reconizer.unfold.domain.entities.*
import pl.reconizer.unfold.domain.repositories.IAdventureRepository
import pl.reconizer.unfold.presentation.common.errorshandlers.ErrorsHandler
import pl.reconizer.unfold.presentation.mvp.PaginatedDataPresenter
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

class AdventuresPresenter(
        backgroundScheduler: Scheduler,
        mainScheduler: Scheduler,
        private val adventuresRepository: IAdventureRepository,
        errorsHandler: ErrorsHandler,
        private val position: Position
) : PaginatedDataPresenter<Adventure, IFilteredAdventuresView>(
        backgroundScheduler,
        mainScheduler,
        errorsHandler
) {

    var sortType: AdventuresSort = AdventuresSort.RATING

    var filters = AdventureFilters(
            MIN_RANGE,
            MAX_RANGE,
            1f
    )
        set(value) {
            hasFiltersChanged = true
            field = value
        }

    var hasFiltersChanged: Boolean = false
        private set

    private var changeNameObservable = PublishSubject.create<String>()

    override fun subscribe(view: IFilteredAdventuresView) {
        super.subscribe(view)
        errorsHandler.view = WeakReference(view)

        disposables.add(
            changeNameObservable
                    .subscribeOn(backgroundScheduler)
                    .filter { it.isEmpty() || it.length >= 3 }
                    .throttleLast(NAME_FILTER_DELAY, TimeUnit.MILLISECONDS, backgroundScheduler)
                    .observeOn(mainScheduler)
                    .subscribe {
                        filters = filters.copy(name = it)
                        fetchFirstPage()
                    }
        )
    }

    override fun fetchFirstPage() {
        hasFiltersChanged = false
        super.fetchFirstPage()
    }

    override fun load(page: Int): Single<ICollectionContainer<Adventure>> {
        return adventuresRepository.searchAdventures(
                page,
                position,
                sortType,
                filters.difficultyLevels,
                filters.name,
                filters.calculatedRangeValue
        )
    }

    fun updateSortType(newSortType: AdventuresSort) {
        sortType = newSortType
        fetchFirstPage()
    }

    fun updateNameFilter(value: String) {
        changeNameObservable.onNext(value)
    }

    companion object {
        const val MIN_RANGE = 1f
        const val MAX_RANGE = 5f
        const val NAME_FILTER_DELAY = 300L
    }

}