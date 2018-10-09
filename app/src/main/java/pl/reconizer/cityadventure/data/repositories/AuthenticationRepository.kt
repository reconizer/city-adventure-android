package pl.reconizer.cityadventure.data.repositories

import io.reactivex.Completable
import io.reactivex.Single
import pl.reconizer.cityadventure.data.local.ILocalStorage
import pl.reconizer.cityadventure.domain.repositories.IAuthenticationRepository

class AuthenticationRepository(private val localStorage: ILocalStorage) : IAuthenticationRepository {

    override fun getToken(): Single<String> {
        return Single.fromCallable { localStorage[TOKEN_KEY] ?: "" }
    }

    override fun saveToken(token: String?): Completable {
        return Completable.fromAction { localStorage[TOKEN_KEY] = token }
    }

    override fun clearToken(): Completable {
        return Completable.fromAction { localStorage.remove(TOKEN_KEY) }
    }

    companion object {
        private const val TOKEN_KEY = "token"
    }

}