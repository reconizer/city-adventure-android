package pl.reconizer.unfold.presentation.adventure.startpoint

import io.reactivex.Scheduler
import pl.reconizer.unfold.domain.entities.MapAdventure
import pl.reconizer.unfold.domain.entities.AdventureStartPoint
import pl.reconizer.unfold.domain.repositories.IAdventureRepository
import pl.reconizer.unfold.presentation.common.errorshandlers.ErrorsHandler
import pl.reconizer.unfold.presentation.common.rx.CompletableCallbackWrapper
import pl.reconizer.unfold.presentation.common.rx.SingleCallbackWrapper
import pl.reconizer.unfold.presentation.mvp.BasePresenter
import java.lang.ref.WeakReference

class StartPointPresenter(
        private val backgroundScheduler: Scheduler,
        private val mainScheduler: Scheduler,
        private val adventureRepository: IAdventureRepository,
        private val errorsHandler: ErrorsHandler,
        val adventure: MapAdventure
) : BasePresenter<IStartPointView>() {

    var adventureStartPoint: AdventureStartPoint? = null
        private set

    override fun subscribe(view: IStartPointView) {
        super.subscribe(view)
        errorsHandler.view = WeakReference(view)
    }

    fun fetchData() {
        disposables.add(
                adventureRepository.getAdventure(adventure.adventureId)
                        .subscribeOn(backgroundScheduler)
                        .observeOn(mainScheduler)
                        .doOnSubscribe { view?.showLoader() }
                        .doFinally { view?.hideLoader() }
                        .subscribeWith(object : SingleCallbackWrapper<AdventureStartPoint>(errorsHandler) {
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
                        .subscribeWith(object : CompletableCallbackWrapper(errorsHandler) {
                            override fun onComplete() {
                                view?.adventureStarted()
                            }
                        })
        )
    }

    fun rateAdventure(rating: Int) {
        disposables.add(
                adventureRepository.rate(adventure.adventureId, rating)
                        .andThen(adventureRepository.getAdventure(adventure.adventureId))
                        .subscribeOn(backgroundScheduler)
                        .observeOn(mainScheduler)
                        .doOnSubscribe { view?.showLoader() }
                        .doFinally { view?.hideLoader() }
                        .subscribeWith(object : SingleCallbackWrapper<AdventureStartPoint>(errorsHandler) {
                            override fun onSuccess(t: AdventureStartPoint) {
                                adventureStartPoint = t
                                view?.show(t)
                            }
                        })
        )
    }
}