package pl.reconizer.unfold.presentation.creatorprofile

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.domain.entities.CreatorAdventure
import pl.reconizer.unfold.domain.entities.CreatorProfile
import pl.reconizer.unfold.domain.entities.ICollectionContainer
import pl.reconizer.unfold.domain.repositories.ICreatorRepository
import pl.reconizer.unfold.presentation.common.rx.CallbackWrapper
import pl.reconizer.unfold.presentation.common.rx.SingleCallbackWrapper
import pl.reconizer.unfold.presentation.errorhandlers.ErrorsHandler
import pl.reconizer.unfold.presentation.mvp.PaginatedDataPresenter
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

class CreatorProfilePresenter(
        backgroundScheduler: Scheduler,
        mainScheduler: Scheduler,
        private val creatorRepository: ICreatorRepository,
        errorsHandler: ErrorsHandler<Error>,
        val creatorId: String
) : PaginatedDataPresenter<CreatorAdventure, ICreatorProfileView>(
        backgroundScheduler,
        mainScheduler,
        errorsHandler
) {

    var profile: CreatorProfile? = null
        private set

    private val followObservable = PublishSubject.create<Boolean>()

    override fun subscribe(view: ICreatorProfileView) {
        super.subscribe(view)
        errorsHandler.view = WeakReference(view)

        disposables.add(
                followObservable
                        .subscribeOn(backgroundScheduler)
                        .observeOn(backgroundScheduler)
                        .throttleFirst(500, TimeUnit.MILLISECONDS, backgroundScheduler)
                        .flatMapSingle {
                            if (it) {
                                creatorRepository.follow(creatorId)
                            } else {
                                creatorRepository.unfollow(creatorId)
                            }
                                    .andThen(creatorRepository.getProfile(creatorId))
                        }
                        .observeOn(mainScheduler)
                        .subscribeWith(object : CallbackWrapper<CreatorProfile, Error>(errorsHandler) {
                            override fun onComplete() {}

                            override fun onNext(t: CreatorProfile) {
                                profile = t
                                this@CreatorProfilePresenter.view?.showProfile()
                            }

                        })
        )
    }

    override fun load(page: Int): Single<ICollectionContainer<CreatorAdventure>> {
        return creatorRepository.getAdventures(page, creatorId)
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

    fun toggleFollow(value: Boolean) {
        followObservable.onNext(value)
    }

}