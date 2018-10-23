package pl.reconizer.cityadventure.data.mappers

import pl.reconizer.cityadventure.data.entities.AuthenticationResponse
import pl.reconizer.cityadventure.domain.common.Mapper

class TokenMapper : Mapper<AuthenticationResponse, String>() {

    override fun map(from: AuthenticationResponse): String {
        return from.jwt
    }

}