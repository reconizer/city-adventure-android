package pl.reconizer.cityadventure.presentation.adventure.journal

import io.reactivex.Scheduler
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.entities.AdventureStartPoint
import pl.reconizer.cityadventure.domain.entities.Clue
import pl.reconizer.cityadventure.domain.repositories.IAdventureRepository
import pl.reconizer.cityadventure.presentation.common.rx.SingleCallbackWrapper
import pl.reconizer.cityadventure.presentation.errorhandlers.ErrorHandler
import pl.reconizer.cityadventure.presentation.mvp.BasePresenter
import java.lang.ref.WeakReference

class JournalPresenter(
        private val backgroundScheduler: Scheduler,
        private val mainScheduler: Scheduler,
        private val adventureRepository: IAdventureRepository,
        private val errorHandler: ErrorHandler<Error>,
        private val adventure: Adventure,
        private val adventureStartPoint: AdventureStartPoint
) : BasePresenter<IJournalView>() {

    var clues: List<Clue> = emptyList()
        private set

    override fun subscribe(view: IJournalView) {
        super.subscribe(view)
        errorHandler.view = WeakReference(view)
    }

    fun fetchClues() {
        disposables.add(
                adventureRepository.getAdventureDiscoveredClues(adventure.adventureId)
                        .subscribeOn(backgroundScheduler)
                        .observeOn(mainScheduler)
                        .subscribeWith(object : SingleCallbackWrapper<List<Clue>, Error>(errorHandler) {
                            override fun onSuccess(t: List<Clue>) {
                                clues = t
                                view?.showClues()
                            }
                        })

        )
    }
}