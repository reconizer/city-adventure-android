package pl.reconizer.cityadventure.data.repositories

import io.reactivex.Single
import pl.reconizer.cityadventure.data.mappers.TokenMapper
import pl.reconizer.cityadventure.data.network.api.IAuthenticationApi
import pl.reconizer.cityadventure.domain.repositories.IUserRepository

class UserRepository(
        private val authenticationApi: IAuthenticationApi
) : IUserRepository{

    private val tokenMapper = TokenMapper()

    override fun login(email: String, password: String): Single<String> {
        return authenticationApi.login(email, password)
                .flatMap { tokenMapper.asyncMap(it) }
    }
}