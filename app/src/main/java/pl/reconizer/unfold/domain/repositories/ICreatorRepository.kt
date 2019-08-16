package pl.reconizer.unfold.domain.repositories

import io.reactivex.Completable
import io.reactivex.Single
import pl.reconizer.unfold.domain.entities.*

interface ICreatorRepository {

    fun getProfile(creatorId: String): Single<CreatorProfile>

    fun search(
            page: Int,
            position: Position,
            isCloseBy: Boolean,
            order: CreatorsSort,
            name: String?
    ): Single<ICollectionContainer<Creator>>

    fun follow(creatorId: String): Completable

    fun unfollow(creatorId: String): Completable

    fun getAdventures(page: Int, creatorId: String): Single<ICollectionContainer<Adventure>>

}