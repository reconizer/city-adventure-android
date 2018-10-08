package pl.reconizer.cityadventure.data.repositories

import io.reactivex.Completable
import io.reactivex.Single
import pl.reconizer.cityadventure.data.local.ILocalStorage
import pl.reconizer.cityadventure.domain.repositories.IAuthenticationRepository

class AuthenticationRepository(private val localStorage: ILocalStorage) : IAuthenticationRepository {

    override fun getToken(): Single<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveToken(token: String?): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clearToken(): Completable {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}