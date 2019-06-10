package pl.reconizer.unfold.data.network.api

import io.reactivex.Completable
import io.reactivex.Single
import pl.reconizer.unfold.domain.entities.CreatorProfile
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ICreatorApi {

    @GET("api/creator/{id}")
    fun getProfile(@Path("id") creatorId: String): Single<CreatorProfile>

    @POST("api/profile/{id}/follow")
    fun follow(@Path("id") creatorId: String): Completable

    @POST("api/profile/{id}/unfollow")
    fun unfollow(@Path("id") creatorId: String): Completable

}