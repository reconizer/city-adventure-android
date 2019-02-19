package pl.reconizer.unfold.presentation.adventure.startpoint

import io.reactivex.Scheduler
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.domain.entities.Adventure
import pl.reconizer.unfold.domain.entities.AdventureStartPoint
import pl.reconizer.unfold.domain.repositories.IAdventureRepository
import pl.reconizer.unfold.presentation.common.rx.CompletableCallbackWrapper
import pl.reconizer.unfold.presentation.common.rx.SingleCallbackWrapper
import pl.reconizer.unfold.presentation.errorhandlers.ErrorHandler
import pl.reconizer.unfold.presentation.mvp.BasePresenter
import java.lang.ref.WeakReference

class StartPointPresenter(
        private val backgroundScheduler: Scheduler,
        private val mainScheduler: Scheduler,
        private val adventureRepository: IAdventureRepository,
        private val errorHandler: ErrorHandler<Error>,
        val adventure: Adventure
) : BasePresenter<IStartPointView>() {

    var adventureStartPoint: AdventureStartPoint? = null
        private set

    override fun subscribe(view: IStartPointView) {
        super.subscribe(view)
        errorHandler.view = WeakReference(view)
    }

    fun fetchData() {
        disposables.add(
                adventureRepository.getAdventure(adventure.adventureId)
                        .subscribeOn(backgroundScheduler)
                        .observeOn(mainScheduler)
                        .doOnSubscribe { view?.showLoader() }
                        .doFinally { view?.hideLoader() }
                        .subscribeWith(object : SingleCallbackWrapper<AdventureStartPoint, Error>(errorHandler) {
                            override fun onSuccess(t: AdventureStartPoint) {
                                adventureStartPoint = t
                                view?.show(t)
                            }
                        })
        )
    }

    fun startAdventure() {
        disposables.add(
                adventureRepository.startAdventure(adventure.adventureId)
                        .subscribeOn(backgroundScheduler)
                        .observeOn(mainScheduler)
                        .doOnSubscribe { view?.showLoader() }
                        .doFinally { view?.hideLoader() }
                        .subscribeWith(object : CompletableCallbackWrapper<Error>(errorHandler) {
                            override fun onComplete() {
                                view?.adventureStarted()
                            }
                        })
        )
    }
}