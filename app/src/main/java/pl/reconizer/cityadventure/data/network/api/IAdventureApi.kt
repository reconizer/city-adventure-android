package pl.reconizer.cityadventure.data.network.api

import io.reactivex.Single
import pl.reconizer.cityadventure.domain.entities.Adventure
import retrofit2.http.GET
import retrofit2.http.Query

interface IAdventureApi {

    @GET("/api/adventures")
    fun getAdventures(
            @Query("lat") lat: Double,
            @Query("lng") lng: Double
    ): Single<List<Adventure>>

}