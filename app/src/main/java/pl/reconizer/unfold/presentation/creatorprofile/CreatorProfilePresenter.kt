package pl.reconizer.unfold.presentation.creatorprofile

import io.reactivex.Scheduler
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.domain.entities.CreatorProfile
import pl.reconizer.unfold.domain.repositories.ICreatorRepository
import pl.reconizer.unfold.presentation.common.rx.SingleCallbackWrapper
import pl.reconizer.unfold.presentation.errorhandlers.ErrorsHandler
import pl.reconizer.unfold.presentation.mvp.BasePresenter
import java.lang.ref.WeakReference

class CreatorProfilePresenter(
        private val backgroundScheduler: Scheduler,
        private val mainScheduler: Scheduler,
        private val creatorRepository: ICreatorRepository,
        private val errorsHandler: ErrorsHandler<Error>,
        val creatorId: String
) : BasePresenter<ICreatorProfileView>() {

    var profile: CreatorProfile? = null
        private set

    override fun subscribe(view: ICreatorProfileView) {
        super.subscribe(view)
        errorsHandler.view = WeakReference(view)
    }

    fun fetchProfile() {
        disposables.add(
                creatorRepository.getProfile(creatorId)
                        .subscribeOn(backgroundScheduler)
                        .observeOn(mainScheduler)
                        .subscribeWith(object : SingleCallbackWrapper<CreatorProfile, Error>(errorsHandler) {
                            override fun onSuccess(t: CreatorProfile) {
                                profile = t
                                view?.showProfile()
                            }
                        })
        )
    }

}