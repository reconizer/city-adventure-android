package pl.reconizer.unfold.data.network.api

import io.reactivex.Single
import pl.reconizer.unfold.domain.entities.UserProfile
import retrofit2.http.GET

interface IUserApi {

    @GET("api/profile")
    fun getProfile(): Single<UserProfile>

}