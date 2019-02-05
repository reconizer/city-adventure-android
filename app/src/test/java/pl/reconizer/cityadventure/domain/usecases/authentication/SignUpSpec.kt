package pl.reconizer.cityadventure.domain.usecases.authentication

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import pl.reconizer.cityadventure.domain.repositories.IAuthenticationRepository
import pl.reconizer.cityadventure.domain.repositories.IUserRepository

class SignUpSpec : Spek({

    describe("sign up usecase") {
        val email = "test@test.com"
        val password = "test"
        val token = "token"
        val scheduler = Schedulers.trampoline()
        val userRepository = mock<IUserRepository>()
        val authenticationRepository = mock<IAuthenticationRepository>()
        val signUp = SignUp(
                scheduler,
                userRepository,
                authenticationRepository
        )

        before {
            whenever(userRepository.register(any(), any())).thenReturn(Single.just(token))
            whenever(authenticationRepository.saveToken(any())).thenReturn(Completable.complete())
        }

        it("signs up a user") {
            signUp(email, password).test().assertNoErrors()
            verify(userRepository, atLeastOnce()).register(email, password)
            verify(authenticationRepository, atLeastOnce()).saveToken(token)
        }
    }
})