package pl.reconizer.cityadventure.presentation.puzzle

import io.reactivex.Maybe
import io.reactivex.Scheduler
import pl.reconizer.cityadventure.common.extensions.toPosition
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.entities.AdventurePoint
import pl.reconizer.cityadventure.domain.entities.PuzzleAnswerForm
import pl.reconizer.cityadventure.domain.entities.PuzzleResponse
import pl.reconizer.cityadventure.domain.repositories.IAdventureRepository
import pl.reconizer.cityadventure.presentation.common.rx.MaybeCallbackWrapper
import pl.reconizer.cityadventure.presentation.common.rx.SingleCallbackWrapper
import pl.reconizer.cityadventure.presentation.errorhandlers.ErrorHandler
import pl.reconizer.cityadventure.presentation.location.GpsInterfaceStatus
import pl.reconizer.cityadventure.presentation.location.ILocationProvider
import pl.reconizer.cityadventure.presentation.mvp.BasePresenter
import java.lang.ref.WeakReference

class PuzzlePresenter(
        private val backgroundScheduler: Scheduler,
        private val mainScheduler: Scheduler,
        private val adventureRepository: IAdventureRepository,
        private val locationProvider: ILocationProvider,
        private val errorHandler: ErrorHandler<Error>,
        private val adventure: Adventure,
        private val adventurePoint: AdventurePoint
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
                            adventure.adventureId,
                            adventurePoint.id,
                            location.toPosition(),
                            answer,
                            null
                    ))
                            .subscribeOn(backgroundScheduler)
                            .observeOn(mainScheduler)
                            .subscribeWith(object : SingleCallbackWrapper<PuzzleResponse, Error>(errorHandler) {
                                override fun onSuccess(t: PuzzleResponse) {
                                    if (t.isCompleted) {

                                    } else {
                                        view?.completedAdventure()
                                        //view?.wrongAnswer()
                                    }
                                }
                            })

            )
        }
    }
}