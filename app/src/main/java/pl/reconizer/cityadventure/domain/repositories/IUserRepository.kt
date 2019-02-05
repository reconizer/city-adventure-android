package pl.reconizer.cityadventure.domain.repositories

import io.reactivex.Single

interface IUserRepository {

    fun login(email: String, password: String): Single<String>

    fun register(
            email: String,
            username: String,
            password: String
    ): Single<String>

}