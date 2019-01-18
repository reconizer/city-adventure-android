package pl.reconizer.cityadventure.data.repositories

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import pl.reconizer.cityadventure.data.entities.AuthenticationResponse
import pl.reconizer.cityadventure.data.mappers.TokenMapper
import pl.reconizer.cityadventure.data.network.api.IAuthenticationApi

class UserRepositorySpec : Spek({

    describe("UserRepository") {
        val api = mock<IAuthenticationApi>()
        val repository = UserRepository(api, TokenMapper())
        val authenticationEntity = AuthenticationResponse("token")

        before { whenever(api.login(any(), any())).thenReturn(Single.just(authenticationEntity)) }

        describe("login") {

            it("performs api call") {
                val email = "testEmail"
                val password = "password"
                val testObservable = repository.login(email, password).test()
                verify(api, atLeastOnce()).login(email, password)
                testObservable.assertValue(authenticationEntity.jwt)
                testObservable.assertComplete()
            }

        }

    }

})