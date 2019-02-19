package pl.reconizer.unfold.presentation.puzzle

import io.reactivex.Scheduler
import pl.reconizer.unfold.common.extensions.toPosition
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.domain.entities.*
import pl.reconizer.unfold.domain.repositories.IAdventureRepository
import pl.reconizer.unfold.presentation.common.rx.SingleCallbackWrapper
import pl.reconizer.unfold.presentation.errorhandlers.ErrorHandler
import pl.reconizer.unfold.presentation.location.GpsInterfaceStatus
import pl.reconizer.unfold.presentation.location.ILocationProvider
import pl.reconizer.unfold.presentation.mvp.BasePresenter
import java.lang.ref.WeakReference

class PuzzlePresenter(
        private val backgroundScheduler: Scheduler,
        private val mainScheduler: Scheduler,
        private val adventureRepository: IAdventureRepository,
        private val locationProvider: ILocationProvider,
        private val errorHandler: ErrorHandler<Error>,
        val adventure: Adventure,
        val adventurePoint: AdventurePoint,
        val puzzleType: PuzzleType
) : BasePresenter<IPuzzleView>() {

    override fun subscribe(view: IPuzzleView) {
        super.subscribe(view)
        errorHandler.view = WeakReference(view)
        if (locationProvider.hasPermission) {
            locationProvider.enable()
            disposables.add(
                    locationProvider.statusChange
                            .subscribeOn(backgroundScheduler)
                            .observeOn(mainScheduler)
                            .subscribe {
                                when(it) {
                                    GpsInterfaceStatus.DOWN -> this@PuzzlePresenter.view?.gpsUnavailable()
                                    GpsInterfaceStatus.UP -> this@PuzzlePresenter.view?.gpsAvailableAgain()
                                }
                            }
            )
        } else {
            this@PuzzlePresenter.view?.requestLocationPermission()
        }
    }

    override fun unsubscribe() {
        super.unsubscribe()
        locationProvider.disable()
    }

    fun resolvePoint(answer: String) {
        locationProvider.lastLocation?.let {location ->
            disposables.add(
                    adventureRepository.resolvePoint(PuzzleAnswerForm(
                            location.toPosition(),
                            adventure.adventureId,
                            answer,
                            puzzleType.name.toLowerCase()
                    ))
                            .subscribeOn(backgroundScheduler)
                            .observeOn(mainScheduler)
                            .doOnSubscribe { view?.showLoader() }
                            .doFinally { view?.hideLoader() }
                            .subscribeWith(object : SingleCallbackWrapper<PuzzleResponse, Error>(errorHandler) {
                                override fun onSuccess(t: PuzzleResponse) {
                                    if (t.isCompleted) {
                                        if (t.isLastPoint) {
                                            view?.completedAdventure()
                                        } else {
                                            view?.correctAnswer()
                                        }
                                    } else {
                                        view?.wrongAnswer()
                                    }
                                }
                            })

            )
        }
    }
}