package pl.reconizer.cityadventure.data.mappers

import pl.reconizer.cityadventure.data.entities.AuthenticationResponse
import pl.reconizer.cityadventure.domain.common.Mapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenMapper @Inject constructor() : Mapper<AuthenticationResponse, String>() {

    override fun map(from: AuthenticationResponse): String {
        return from.jwt
    }

}