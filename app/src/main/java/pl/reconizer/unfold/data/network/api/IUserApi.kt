package pl.reconizer.unfold.data.network.api

import io.reactivex.Completable
import io.reactivex.Single
import pl.reconizer.unfold.domain.entities.Avatar
import pl.reconizer.unfold.domain.entities.UserProfile
import pl.reconizer.unfold.domain.entities.forms.UserProfileForm
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface IUserApi {

    @GET("api/profile")
    fun getProfile(): Single<UserProfile>

    @POST("api/profile/update")
    fun updateProfile(@Body form: UserProfileForm): Completable

    @GET("api/profile/avatar_list")
    fun getAvatars(): Single<List<Avatar>>

}