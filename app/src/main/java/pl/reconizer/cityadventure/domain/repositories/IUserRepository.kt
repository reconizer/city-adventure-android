package pl.reconizer.cityadventure.domain.repositories

import io.reactivex.Completable
import io.reactivex.Single

interface IUserRepository {

    fun login(email: String, password: String): Single<String>

    fun register(
            email: String,
            username: String,
            password: String
    ): Single<String>

    fun sendResetPasswordCode(email: String): Completable

    fun resetPassword(code: String, newPassword: String): Completable

}