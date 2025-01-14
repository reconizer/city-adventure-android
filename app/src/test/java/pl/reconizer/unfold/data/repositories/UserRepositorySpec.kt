package pl.reconizer.unfold.data.repositories

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import pl.reconizer.unfold.data.entities.AuthenticationResponse
import pl.reconizer.unfold.data.mappers.TokenMapper
import pl.reconizer.unfold.data.network.api.IAuthenticationApi

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

        describe("register") {

            it ("completes") {
                val email = "testEmail"
                val username = "test-user"
                val password = "password"
                val testObservable = repository.register(email, username, password).test()
                testObservable.assertValue("dev")
                testObservable.assertComplete()
            }

        }

        describe("sendResetPasswordCode") {

            it ("completes") {
                val email = "testEmail"
                val testObservable = repository.sendResetPasswordCode(email).test()
                testObservable.assertComplete()
            }

        }

        describe("resetPassword") {

            it ("completes") {
                val code = "code"
                val password = "password"
                val testObservable = repository.resetPassword(code, password).test()
                testObservable.assertComplete()
            }

        }

    }

})