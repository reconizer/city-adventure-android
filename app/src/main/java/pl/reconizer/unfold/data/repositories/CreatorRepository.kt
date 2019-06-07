package pl.reconizer.unfold.data.repositories

import io.reactivex.Single
import pl.reconizer.unfold.data.network.api.ICreatorApi
import pl.reconizer.unfold.domain.entities.CreatorProfile
import pl.reconizer.unfold.domain.repositories.ICreatorRepository

class CreatorRepository(
        private val creatorApi: ICreatorApi
) : ICreatorRepository {

    override fun getProfile(creatorId: String): Single<CreatorProfile> {
        return creatorApi.getProfile(creatorId)
    }

}