package pl.reconizer.unfold.data.network.api

import io.reactivex.Completable
import io.reactivex.Single
import pl.reconizer.unfold.data.entities.AdventurePointWithCluesResponse
import pl.reconizer.unfold.data.entities.AdventureStartPointResponse
import pl.reconizer.unfold.data.entities.RatingResponse
import pl.reconizer.unfold.domain.entities.*
import pl.reconizer.unfold.domain.entities.puzzles.PuzzleAnswerForm
import pl.reconizer.unfold.domain.entities.puzzles.PuzzleResponse
import retrofit2.http.*

interface IAdventureApi {

    @GET("api/adventures")
    fun getAdventures(
            @Query("lat") lat: Double,
            @Query("lng") lng: Double
    ): Single<List<MapAdventure>>

    @GET("api/adventures/filters")
    fun searchAdventures(
            @Query("page") page: Int,
            @Query("lat") lat: Double,
            @Query("lng") lng: Double,
            @Query("filters[name]") name: String?,
            @Query("filters[difficulty_level]") difficultyLevel: Int?,
            @Query("filters[range]") range: Float?,
            @Query("orders") order: String?
    ): Single<List<Adventure>>

    @GET("api/adventures/{id}")
    fun getAdventure(
        @Path("id") adventureId: String
    ): Single<AdventureStartPointResponse>

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

    @POST("api/adventures/rating")
    fun rate(
            @Query("adventure_id") adventureId: String,
            @Query("rating") rating: Int
    ): Single<RatingResponse>

    @GET("api/adventures/user")
    fun getUserAdventures(
            @Query("page") page: Int,
            @Query("completed") completed: Boolean,
            @Query("paid") paid: Boolean
    ): Single<List<UserAdventure>>

    @GET("api/creator/adventures")
    fun getCreatorAdventures(
            @Query("filter[page]") page: Int,
            @Query("creator_id") creatorId: String
    ): Single<List<Adventure>>

}