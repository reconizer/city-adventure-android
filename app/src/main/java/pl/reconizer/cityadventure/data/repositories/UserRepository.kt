package pl.reconizer.cityadventure.data.repositories

import io.reactivex.Completable
import io.reactivex.Single
import pl.reconizer.cityadventure.data.mappers.TokenMapper
import pl.reconizer.cityadventure.data.network.api.IAuthenticationApi
import pl.reconizer.cityadventure.domain.repositories.IUserRepository

class UserRepository(
        private val authenticationApi: IAuthenticationApi,
        private val tokenMapper: TokenMapper
) : IUserRepository{

    override fun login(email: String, password: String): Single<String> {
        return authenticationApi.login(email, password)
                .flatMap { tokenMapper.asyncMap(it) }
    }

    override fun register(
            email: String,
            username: String,
            password: String
    ): Single<String> {
        return Single.just("dev")
    }

    override fun sendResetPasswordCode(email: String): Completable {
        return Completable.complete()
    }

    override fun resetPassword(code: String, newPassword: String): Completable {
        return Completable.complete()
    }

}