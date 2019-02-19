package pl.reconizer.unfold.presentation.authentication.resetpassword.firststep

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.domain.repositories.IUserRepository
import pl.reconizer.unfold.presentation.errorhandlers.ErrorHandler

class ResetPasswordFirstStepPresenterSpec : Spek({

    describe("ResetPasswordFirstStepPresenter") {
        val view = mock<IResetPasswordFirstStepView>()
        val errorHandler = mock<ErrorHandler<Error>>()
        val repository = mock<IUserRepository>()

        val presenter = ResetPasswordFirstStepPresenter(
                Schedulers.trampoline(),
                Schedulers.trampoline(),
                repository,
                errorHandler
        )

        beforeEachTest {
            reset(view, errorHandler, repository)
        }

        describe("subscribe") {


            it("sets view for error handler") {
                presenter.subscribe(view)
                verify(errorHandler, atLeastOnce()).view = any()
            }

        }

        describe("sendCode") {

            context("when email not empty") {
                val email = "test@email"

                beforeEachTest {
                    whenever(repository.sendResetPasswordCode(email)).thenReturn(Completable.complete())
                    presenter.sendCode(email)
                }

                it("shows and hides loader") {
                    verify(view, atLeastOnce()).showLoader()
                    verify(view, atLeastOnce()).hideLoader()
                }

                it("sends a code to user") {
                    verify(repository, atLeastOnce()).sendResetPasswordCode(email)
                }

                it("notifies view") {
                    verify(view, atLeastOnce()).codeSent()
                }
            }

        }
    }

})