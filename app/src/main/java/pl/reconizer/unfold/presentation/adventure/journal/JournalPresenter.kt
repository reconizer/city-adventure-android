package pl.reconizer.unfold.presentation.adventure.journal

import io.reactivex.Scheduler
import pl.reconizer.unfold.domain.entities.MapAdventure
import pl.reconizer.unfold.domain.entities.AdventurePointWithClues
import pl.reconizer.unfold.domain.entities.AdventureStartPoint
import pl.reconizer.unfold.domain.repositories.IAdventureRepository
import pl.reconizer.unfold.presentation.common.errorshandlers.ErrorsHandler
import pl.reconizer.unfold.presentation.common.rx.SingleCallbackWrapper
import pl.reconizer.unfold.presentation.mvp.BasePresenter
import java.lang.ref.WeakReference

class JournalPresenter(
        private val backgroundScheduler: Scheduler,
        private val mainScheduler: Scheduler,
        private val adventureRepository: IAdventureRepository,
        private val errorsHandler: ErrorsHandler,
        private val adventure: MapAdventure,
        private val adventureStartPoint: AdventureStartPoint
) : BasePresenter<IJournalView>() {

    var points: List<AdventurePointWithClues> = emptyList()
        private set

    override fun subscribe(view: IJournalView) {
        super.subscribe(view)
        errorsHandler.view = WeakReference(view)
    }

    fun fetchClues() {
        disposables.add(
                adventureRepository.getAdventureDiscoveredClues(adventure.adventureId)
                        .subscribeOn(backgroundScheduler)
                        .observeOn(mainScheduler)
                        .doOnSubscribe { view?.showLoader() }
                        .doFinally { view?.hideLoader() }
                        .subscribeWith(object : SingleCallbackWrapper<List<AdventurePointWithClues>>(errorsHandler) {
                            override fun onSuccess(t: List<AdventurePointWithClues>) {
                                points = t
                                view?.showClues()
                            }
                        })

        )
    }
}