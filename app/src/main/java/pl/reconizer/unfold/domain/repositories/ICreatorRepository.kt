package pl.reconizer.unfold.domain.repositories

import io.reactivex.Completable
import io.reactivex.Single
import pl.reconizer.unfold.domain.entities.CreatorProfile

interface ICreatorRepository {

    fun getProfile(creatorId: String): Single<CreatorProfile>

    fun follow(creatorId: String): Completable

    fun unfollow(creatorId: String): Completable

}