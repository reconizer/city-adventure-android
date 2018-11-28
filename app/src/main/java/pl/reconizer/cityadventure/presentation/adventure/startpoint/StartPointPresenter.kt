package pl.reconizer.cityadventure.presentation.adventure.startpoint

import io.reactivex.Scheduler
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.entities.AdventureStartPoint
import pl.reconizer.cityadventure.domain.usecases.adventure.GetAdventureStartPoint
import pl.reconizer.cityadventure.presentation.common.rx.SingleCallbackWrapper
import pl.reconizer.cityadventure.presentation.errorhandlers.ErrorHandler
import pl.reconizer.cityadventure.presentation.mvp.BasePresenter
import java.lang.ref.WeakReference

class StartPointPresenter(
        private val backgroundScheduler: Scheduler,
        private val mainScheduler: Scheduler,
        private val getAdventureStartPoint: GetAdventureStartPoint,
        private val errorHandler: ErrorHandler<Error>,
        private val adventure: Adventure
) : BasePresenter<IStartPointView>() {

    var adventureStartPoint: AdventureStartPoint? = null
        private set

    override fun subscribe(view: IStartPointView) {
        super.subscribe(view)
        errorHandler.view = WeakReference(view)
    }

    fun fetchData() {
        disposables.add(
                getAdventureStartPoint(adventure.adventureId)
                        .subscribeOn(backgroundScheduler)
                        .observeOn(mainScheduler)
                        .subscribeWith(object : SingleCallbackWrapper<AdventureStartPoint, Error>(errorHandler) {
                            override fun onSuccess(t: AdventureStartPoint) {
                                adventureStartPoint = t
                                view?.show(t)
                            }

                        })
        )
    }
}