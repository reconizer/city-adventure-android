package pl.reconizer.unfold.data.network.api

import io.reactivex.Single
import pl.reconizer.unfold.domain.entities.CreatorProfile
import retrofit2.http.GET
import retrofit2.http.Path

interface ICreatorApi {

    @GET("api/creator/{id}")
    fun getProfile(@Path("id") creatorId: String): Single<CreatorProfile>

}