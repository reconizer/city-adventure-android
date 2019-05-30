package pl.reconizer.unfold.domain.repositories

import io.reactivex.Completable
import io.reactivex.Single
import pl.reconizer.unfold.domain.entities.UserProfile

interface IUserRepository {

    fun login(email: String, password: String): Single<String>

    fun register(
            email: String,
            username: String,
            password: String
    ): Single<String>

    fun sendResetPasswordCode(email: String): Completable

    fun resetPassword(code: String, newPassword: String): Completable

    fun getProfile(): Single<UserProfile>

}