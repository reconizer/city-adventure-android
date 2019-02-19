package pl.reconizer.unfold.data.mappers

import pl.reconizer.unfold.data.entities.AuthenticationResponse
import pl.reconizer.unfold.domain.common.Mapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenMapper @Inject constructor() : Mapper<AuthenticationResponse, String>() {

    override fun map(from: AuthenticationResponse): String {
        return from.jwt
    }

}