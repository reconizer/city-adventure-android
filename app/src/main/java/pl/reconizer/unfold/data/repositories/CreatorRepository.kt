package pl.reconizer.unfold.data.repositories

import io.reactivex.Completable
import io.reactivex.Single
import pl.reconizer.unfold.data.network.api.IAdventureApi
import pl.reconizer.unfold.data.network.api.ICreatorApi
import pl.reconizer.unfold.domain.entities.CollectionContainer
import pl.reconizer.unfold.domain.entities.Adventure
import pl.reconizer.unfold.domain.entities.CreatorProfile
import pl.reconizer.unfold.domain.entities.ICollectionContainer
import pl.reconizer.unfold.domain.repositories.ICreatorRepository

class CreatorRepository(
        private val creatorApi: ICreatorApi,
        private val adventureApi: IAdventureApi
) : ICreatorRepository {

    override fun getProfile(creatorId: String): Single<CreatorProfile> {
        return creatorApi.getProfile(creatorId)
    }

    override fun follow(creatorId: String): Completable {
        return creatorApi.follow(creatorId)
    }

    override fun unfollow(creatorId: String): Completable {
        return creatorApi.unfollow(creatorId)
    }

    override fun getAdventures(page: Int, creatorId: String): Single<ICollectionContainer<Adventure>> {
        return adventureApi.getCreatorAdventures(page, creatorId)
                .map { CollectionContainer(it) }
    }

}