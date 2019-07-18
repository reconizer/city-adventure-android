package pl.reconizer.unfold.presentation.mvp

import io.reactivex.Scheduler
import io.reactivex.Single
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.domain.entities.ICollectionContainer
import pl.reconizer.unfold.presentation.common.rx.SingleCallbackWrapper
import pl.reconizer.unfold.presentation.errorhandlers.ErrorsHandler

abstract class PaginatedDataPresenter<TEntity, TView: IListView<TEntity>>(
        protected val backgroundScheduler: Scheduler,
        protected val mainScheduler: Scheduler,
        protected val errorsHandler: ErrorsHandler<Error>
) : BasePresenter<TView>(), IPaginatedDataPresenter<TEntity>, IPaginatedLoader<TEntity> {

    override var items: List<TEntity> = emptyList()
        protected set

    override var page: Int = 0
        protected set

    override var requestedPage: Int = 0
        protected set

    override var hasGotMorePages: Boolean = true
        protected set

    override fun resetPagination() {
        page = 0
        hasGotMorePages = true
    }

    override fun fetchFirstPage() {
        resetPagination()
        fetchNextPage()
    }

    override fun fetchNextPage() {
        if (hasGotMorePages) {
            hasGotMorePages = false
            requestedPage = page + 1
            disposables.add(
                    load(requestedPage)
                            .subscribeOn(backgroundScheduler)
                            .observeOn(mainScheduler)
                            .doOnSubscribe { view?.showListLoader() }
                            .doFinally { view?.hideListLoader() }
                            .subscribeWith(object : SingleCallbackWrapper<ICollectionContainer<TEntity>, Error>(errorsHandler) {
                                override fun onSuccess(t: ICollectionContainer<TEntity>) {
                                    hasGotMorePages = t.collection.isNotEmpty()
                                    if ((t.collection.isNotEmpty() && page != requestedPage) || page == 0) {
                                        page++
                                        if (page == 1) items = emptyList()
                                        val newCollection = mutableListOf<TEntity>().apply {
                                            if (page > 1) addAll(items)
                                            addAll(t.collection)
                                        }
                                        items = newCollection
                                        if (page == 1) {
                                            view?.showFirstPage(t.collection)
                                        } else {
                                            view?.showNextPage(t.collection)
                                        }
                                    }
                                }

                            })
            )
        }
    }

    abstract override fun load(page: Int): Single<ICollectionContainer<TEntity>>
}