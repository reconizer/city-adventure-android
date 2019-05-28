package pl.reconizer.unfold.presentation.adventure.summary

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.domain.entities.*
import pl.reconizer.unfold.domain.repositories.IAdventureRepository
import pl.reconizer.unfold.presentation.common.rx.CompletableCallbackWrapper
import pl.reconizer.unfold.presentation.common.rx.SingleCallbackWrapper
import pl.reconizer.unfold.presentation.errorhandlers.ErrorHandler
import pl.reconizer.unfold.presentation.mvp.BasePresenter
import java.lang.ref.WeakReference

class AdventureSummaryPresenter(
        private val backgroundScheduler: Scheduler,
        private val mainScheduler: Scheduler,
        private val adventureRepository: IAdventureRepository,
        private val errorHandler: ErrorHandler<Error>,
        private val adventure: Adventure
) : BasePresenter<IAdventureSummaryView>() {

    var userRanking: RankingEntry? = null

    var summary: List<RankingEntry> = emptyList()

    override fun subscribe(view: IAdventureSummaryView) {
        super.subscribe(view)
        errorHandler.view = WeakReference(view)
    }

    fun fetchData() {
        disposables.add(
                Single.zip(
                        adventureRepository.userAdventureRanking(adventure.adventureId)
                                .observeOn(mainScheduler)
                                .doOnSuccess {
                                    userRanking = it
                                    view?.showUserRanking()
                                },
                        adventureRepository.getSummary(adventure.adventureId)
                                .observeOn(mainScheduler)
                                .doOnSuccess {
                                    summary = it
                                    view?.showSummary()
                                },
                        BiFunction {ranking: RankingEntry, summary: List<RankingEntry> -> Pair(ranking, summary)}
                )
                        .subscribeOn(backgroundScheduler)
                        .observeOn(mainScheduler)
                        .doOnSubscribe { view?.showLoader() }
                        .doFinally { view?.hideLoader() }
                        .subscribeWith(object : SingleCallbackWrapper<Pair<RankingEntry, List<RankingEntry>>, Error>(errorHandler) {
                            override fun onSuccess(t: Pair<RankingEntry, List<RankingEntry>>) {
                            }
                        })
        )
    }

    fun rateAdventure(rating: Int) {
        disposables.add(
                adventureRepository.rate(adventure.adventureId, rating)
                        .subscribeOn(backgroundScheduler)
                        .observeOn(mainScheduler)
                        .doOnSubscribe { view?.showLoader() }
                        .doFinally { view?.hideLoader() }
                        .subscribeWith(object : CompletableCallbackWrapper<Error>(errorHandler) {
                            override fun onComplete() {

                            }
                        })
        )
    }

}