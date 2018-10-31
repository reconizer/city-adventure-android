package pl.reconizer.cityadventure.data.mappers

import pl.reconizer.cityadventure.data.entities.Authentication
import pl.reconizer.cityadventure.domain.common.Mapper

class TokenMapper : Mapper<Authentication, String>() {

    override fun map(from: Authentication): String {
        return from.jwt
    }

}