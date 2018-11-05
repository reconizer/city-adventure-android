package pl.reconizer.cityadventure.data.network.api

import io.reactivex.Single
import pl.reconizer.cityadventure.data.entities.AuthenticationResponse
import retrofit2.http.POST
import retrofit2.http.Query

interface IAuthenticationApi {

    @POST("/auth")
    fun login(
            @Query("email") email: String,
            @Query("password") password: String
    ): Single<AuthenticationResponse>

}