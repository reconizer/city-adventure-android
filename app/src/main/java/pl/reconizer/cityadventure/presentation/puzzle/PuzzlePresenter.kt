package pl.reconizer.cityadventure.presentation.puzzle

import io.reactivex.Scheduler
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.entities.AdventurePoint
import pl.reconizer.cityadventure.domain.entities.PuzzleAnswerForm
import pl.reconizer.cityadventure.domain.entities.PuzzleResponse
import pl.reconizer.cityadventure.domain.repositories.IAdventureRepository
import pl.reconizer.cityadventure.presentation.common.rx.SingleCallbackWrapper
import pl.reconizer.cityadventure.presentation.errorhandlers.ErrorHandler
import pl.reconizer.cityadventure.presentation.mvp.BasePresenter
import java.lang.ref.WeakReference

class PuzzlePresenter(
        private val backgroundScheduler: Scheduler,
        private val mainScheduler: Scheduler,
        private val adventureRepository: IAdventureRepository,
        private val errorHandler: ErrorHandler<Error>,
        private val adventure: Adventure,
        private val adventurePoint: AdventurePoint
) : BasePresenter<IPuzzleView>() {

    override fun subscribe(view: IPuzzleView) {
        super.subscribe(view)
        errorHandler.view = WeakReference(view)
    }

    fun resolvePoint(puzzleForm: PuzzleAnswerForm) {
        disposables.add(
                adventureRepository.resolvePoint(puzzleForm)
                        .subscribeOn(backgroundScheduler)
                        .observeOn(mainScheduler)
                        .subscribeWith(object : SingleCallbackWrapper<PuzzleResponse, Error>(errorHandler) {
                            override fun onSuccess(t: PuzzleResponse) {

                            }
                        })

        )
    }
}