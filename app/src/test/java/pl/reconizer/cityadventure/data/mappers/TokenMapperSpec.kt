package pl.reconizer.cityadventure.data.mappers

import com.winterbe.expekt.expect
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import pl.reconizer.cityadventure.data.entities.Authentication

class TokenMapperSpec : Spek({

    describe("TokenMapper") {
        lateinit var authenticationEntity: Authentication
        val token = "token"

        beforeEachTest { authenticationEntity = Authentication(token) }

        it("correctly maps the entity") {
            expect(TokenMapper().map(authenticationEntity)).to.equal(token)
        }
    }

})