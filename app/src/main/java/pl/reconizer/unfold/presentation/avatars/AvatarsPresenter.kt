package pl.reconizer.unfold.presentation.avatars

import io.reactivex.Scheduler
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.domain.entities.Avatar
import pl.reconizer.unfold.domain.repositories.IUserRepository
import pl.reconizer.unfold.presentation.common.rx.SingleCallbackWrapper
import pl.reconizer.unfold.presentation.errorhandlers.ErrorsHandler
import pl.reconizer.unfold.presentation.mvp.BasePresenter
import java.lang.ref.WeakReference

class AvatarsPresenter(
        private val backgroundScheduler: Scheduler,
        private val mainScheduler: Scheduler,
        private val userRepository: IUserRepository,
        private val errorsHandler: ErrorsHandler<Error>
) : BasePresenter<IChooseAvatarView>() {

    var avatars: List<Avatar> = emptyList()
        private set

    override fun subscribe(view: IChooseAvatarView) {
        super.subscribe(view)
        errorsHandler.view = WeakReference(view)
    }

    fun fetchAvatars() {
        disposables.add(
                userRepository.getAvatars()
                        .subscribeOn(backgroundScheduler)
                        .observeOn(mainScheduler)
                        .subscribeWith(object : SingleCallbackWrapper<List<Avatar>, Error>(errorsHandler) {
                            override fun onSuccess(t: List<Avatar>) {
                                avatars = t
                                view?.showAvatars()
                            }
                        })
        )
    }

}