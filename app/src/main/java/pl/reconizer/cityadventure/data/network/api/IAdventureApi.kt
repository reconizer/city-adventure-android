package pl.reconizer.cityadventure.data.network.api

import io.reactivex.Completable
import io.reactivex.Single
import pl.reconizer.cityadventure.data.entities.AdventurePointWithCluesResponse
import pl.reconizer.cityadventure.domain.entities.*
import retrofit2.http.*

interface IAdventureApi {

    @GET("/api/adventures")
    fun getAdventures(
            @Query("lat") lat: Double,
            @Query("lng") lng: Double
    ): Single<List<Adventure>>

    @GET("/api/adventures/{id}")
    fun getAdventure(
        @Path("id") adventureId: String
    ): Single<AdventureStartPoint>

    @GET("api/clues/{id}")
    fun getAdventureDiscoveredClues(
            @Path("id") adventureId: String
    ): Single<List<AdventurePointWithCluesResponse>>

    @GET("api/adventures/{id}/completed_points")
    fun getAdventureCompletedPoints(
            @Path("id") adventureId: String
    ): Single<List<AdventurePoint>>

    @POST("api/adventures/start")
    fun startAdventure(
            @Query("adventure_id") adventureId: String
    ): Completable

    @POST("api/points/resolve_point")
    fun resolvePoint(
            @Body form: PuzzleAnswerForm
    ): Single<PuzzleResponse>

    @GET("api/adventures/{id}/current_user_ranking")
    fun userAdventureRanking(
            @Path("id") adventureId: String
    ): Single<RankingEntry>

    @GET("/api/adventures/summary/{id}")
    fun getSummary(
            @Path("id") adventureId: String
    ): Single<List<RankingEntry>>

}