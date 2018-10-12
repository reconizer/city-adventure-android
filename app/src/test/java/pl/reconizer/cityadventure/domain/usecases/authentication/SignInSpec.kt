package pl.reconizer.cityadventure.domain.usecases.authentication

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import pl.reconizer.cityadventure.domain.repositories.IAuthenticationRepository
import pl.reconizer.cityadventure.domain.repositories.IUserRepository

class SignInSpec : Spek({

    describe("sign in usecase") {
        val email = "test@test.com"
        val password = "test"
        val token = "token"
        val scheduler = Schedulers.trampoline()
        val userRepository = mock<IUserRepository>()
        val authenticationRepository = mock<IAuthenticationRepository>()
        val signIn = SignIn(
                scheduler,
                userRepository,
                authenticationRepository
        )

        before {
            whenever(userRepository.login(any(), any())).thenReturn(Single.just(token))
            whenever(authenticationRepository.saveToken(any())).thenReturn(Completable.complete())
        }

        it("signs in a user") {
            signIn(email, password).test().assertNoErrors()
            verify(userRepository, atLeastOnce()).login(email, password)
            verify(authenticationRepository, atLeastOnce()).saveToken(token)
        }
    }
})