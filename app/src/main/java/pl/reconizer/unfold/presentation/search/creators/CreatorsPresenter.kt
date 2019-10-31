package pl.reconizer.unfold.presentation.search.creators

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import pl.reconizer.unfold.domain.entities.*
import pl.reconizer.unfold.domain.repositories.ICreatorRepository
import pl.reconizer.unfold.presentation.common.errorshandlers.ErrorsHandler
import pl.reconizer.unfold.presentation.mvp.PaginatedDataPresenter
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

class CreatorsPresenter(
        backgroundScheduler: Scheduler,
        mainScheduler: Scheduler,
        private val creatorRepository: ICreatorRepository,
        errorsHandler: ErrorsHandler,
        private val position: Position
) : PaginatedDataPresenter<Creator, IFilteredCreatorsView>(
        backgroundScheduler,
        mainScheduler,
        errorsHandler
) {

    var sortType: CreatorsSort = CreatorsSort.RATING
    var displayCloseByCreators: Boolean = false
    var nameFilter: String = ""

    private var changeNameObservable = PublishSubject.create<String>()

    override fun subscribe(view: IFilteredCreatorsView) {
        super.subscribe(view)
        errorsHandler.view = WeakReference(view)

        disposables.add(
                changeNameObservable
                        .subscribeOn(backgroundScheduler)
                        .filter { it.isEmpty() || it.length >= 3 }
                        .throttleLast(NAME_FILTER_DELAY, TimeUnit.MILLISECONDS, backgroundScheduler)
                        .observeOn(mainScheduler)
                        .subscribe {
                            nameFilter = it
                            fetchFirstPage()
                        }
        )
    }

    override fun load(page: Int): Single<ICollectionContainer<Creator>> {
        return creatorRepository.search(
                page,
                position,
                displayCloseByCreators,
                sortType,
                nameFilter
        )
    }

    fun updateSortType(newSortType: CreatorsSort) {
        sortType = newSortType
        fetchFirstPage()
    }

    fun updateDisplayCloseByCreatorsFlag(value: Boolean) {
        displayCloseByCreators = value
        fetchFirstPage()
    }

    fun updateNameFilter(value: String) {
        changeNameObservable.onNext(value)
    }

    companion object {
        const val NAME_FILTER_DELAY = 300L
    }

}