package pl.reconizer.unfold.domain.repositories

import io.reactivex.Completable
import io.reactivex.Single

interface IAuthenticationRepository {

    fun getToken(): Single<String>

    fun saveToken(token: String?): Completable

    fun clearToken(): Completable

}