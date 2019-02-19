package pl.reconizer.unfold.data.mappers

import com.winterbe.expekt.expect
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import pl.reconizer.unfold.data.entities.AuthenticationResponse

class TokenMapperSpec : Spek({

    describe("TokenMapper") {
        lateinit var authenticationEntity: AuthenticationResponse
        val token = "token"

        beforeEachTest { authenticationEntity = AuthenticationResponse(token) }

        it("correctly maps the entity") {
            expect(TokenMapper().map(authenticationEntity)).to.equal(token)
        }
    }

})