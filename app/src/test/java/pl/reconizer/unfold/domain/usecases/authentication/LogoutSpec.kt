package pl.reconizer.unfold.domain.usecases.authentication

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import pl.reconizer.unfold.domain.repositories.IAuthenticationRepository

class LogoutSpec : Spek({

    describe("logout usecase") {
        val scheduler = Schedulers.trampoline()
        val authenticationRepository = mock<IAuthenticationRepository>()
        val logout = Logout(
                scheduler,
                authenticationRepository
        )

        before {
            whenever(authenticationRepository.clearToken()).thenReturn(Completable.complete())
        }

        it("logs out a user") {
            logout().test().assertNoErrors()
            verify(authenticationRepository, atLeastOnce()).clearToken()
        }
    }
})