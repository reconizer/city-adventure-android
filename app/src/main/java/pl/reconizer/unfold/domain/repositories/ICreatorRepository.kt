package pl.reconizer.unfold.domain.repositories

import io.reactivex.Completable
import io.reactivex.Single
import pl.reconizer.unfold.domain.entities.CreatorAdventure
import pl.reconizer.unfold.domain.entities.CreatorProfile
import pl.reconizer.unfold.domain.entities.ICollectionContainer

interface ICreatorRepository {

    fun getProfile(creatorId: String): Single<CreatorProfile>

    fun follow(creatorId: String): Completable

    fun unfollow(creatorId: String): Completable

    fun getAdventures(page: Int, creatorId: String): Single<ICollectionContainer<CreatorAdventure>>

}