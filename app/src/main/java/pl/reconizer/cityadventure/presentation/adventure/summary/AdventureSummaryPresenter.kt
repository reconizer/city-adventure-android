package pl.reconizer.cityadventure.presentation.adventure.summary

import io.reactivex.Scheduler
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.domain.entities.*
import pl.reconizer.cityadventure.domain.repositories.IAdventureRepository
import pl.reconizer.cityadventure.presentation.common.rx.SingleCallbackWrapper
import pl.reconizer.cityadventure.presentation.errorhandlers.ErrorHandler
import pl.reconizer.cityadventure.presentation.mvp.BasePresenter
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

    fun fetchUserRanking() {
        disposables.add(
                adventureRepository.userAdventureRanking(adventure.adventureId)
                        .subscribeOn(backgroundScheduler)
                        .observeOn(mainScheduler)
                        .subscribeWith(object : SingleCallbackWrapper<RankingEntry, Error>(errorHandler) {
                            override fun onSuccess(t: RankingEntry) {
                                userRanking = t
                                view?.showUserRanking()
                            }
                        })

        )
    }

    fun fetchSummary() {
        disposables.add(
                adventureRepository.getSummary(adventure.adventureId)
                        .subscribeOn(backgroundScheduler)
                        .observeOn(mainScheduler)
                        .subscribeWith(object : SingleCallbackWrapper<List<RankingEntry>, Error>(errorHandler) {
                            override fun onSuccess(t: List<RankingEntry>) {
                                summary = t
                                view?.showSummary()
                            }
                        })

        )
    }

}