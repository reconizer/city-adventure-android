package pl.reconizer.unfold.presentation.authentication.resetpassword.secondstep

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.domain.repositories.IUserRepository
import pl.reconizer.unfold.presentation.errorhandlers.ErrorsHandler

class ResetPasswordSecondStepPresenterSpec : Spek({

    describe("ResetPasswordSecondStepPresenter") {
        val view = mock<IResetPasswordSecondStepView>()
        val errorHandler = mock<ErrorsHandler<Error>>()
        val repository = mock<IUserRepository>()

        val presenter = ResetPasswordSecondStepPresenter(
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

        describe("resetPassword") {

            context("when form is valid") {
                val form = Form(
                        code = "1234",
                        password = "test",
                        passwordConfirmation = "test"
                )

                beforeEachTest {
                    whenever(repository.resetPassword(form.code, form.password)).thenReturn(Completable.complete())
                    presenter.resetPassword(form)
                }

                it("shows and hides loader") {
                    verify(view, atLeastOnce()).showLoader()
                    verify(view, atLeastOnce()).hideLoader()
                }

                it("resets a password") {
                    verify(repository, atLeastOnce()).resetPassword(form.code, form.password)
                }

                it("notifies view") {
                    verify(view, atLeastOnce()).successfulPasswordReset()
                }
            }

        }
    }

})