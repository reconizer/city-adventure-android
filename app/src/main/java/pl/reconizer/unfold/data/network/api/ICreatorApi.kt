package pl.reconizer.unfold.data.network.api

import io.reactivex.Completable
import io.reactivex.Single
import pl.reconizer.unfold.domain.entities.Creator
import pl.reconizer.unfold.domain.entities.CreatorProfile
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ICreatorApi {

    @GET("api/creator/{id}")
    fun getProfile(@Path("id") creatorId: String): Single<CreatorProfile>

    @POST("api/profile/{id}/follow")
    fun follow(@Path("id") creatorId: String): Completable

    @POST("api/profile/{id}/unfollow")
    fun unfollow(@Path("id") creatorId: String): Completable

    @GET("api/creator/filters")
    fun searchCreator(
            @Query("page") page: Int,
            @Query("lat") lat: Double,
            @Query("lng") lng: Double,
            @Query("filters[range]") closeBy: Boolean,
            @Query("filters[name]") name: String?,
            @Query("orders") order: String?
    ): Single<List<Creator>>

}

