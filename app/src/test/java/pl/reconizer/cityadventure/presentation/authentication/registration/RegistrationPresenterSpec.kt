package pl.reconizer.cityadventure.presentation.authentication.registration

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.domain.usecases.authentication.SignUp
import pl.reconizer.cityadventure.presentation.errorhandlers.ErrorHandler

class RegistrationPresenterSpec : Spek({

    describe("RegistrationPresenter") {
        val view = mock<IRegistrationView>()
        val scheduler = Schedulers.trampoline()
        val errorHandler = mock<ErrorHandler<Error>>()
        val signUp = mock<SignUp>()

        val presenter = RegistrationPresenter(scheduler, signUp, errorHandler)

        beforeEachTest {
            reset(view, errorHandler, signUp)
        }

        describe("subscribe") {


            it("sets view for error handler") {
                presenter.subscribe(view)
                verify(errorHandler, atLeastOnce()).view = any()
            }

        }

        describe("register") {

            context("when form is valid") {
                val form = Form(
                        email = "test@test.com",
                        username = "testname",
                        password = "test",
                        passwordConfirmation = "test"
                )

                beforeEachTest {
                    whenever(signUp.invoke(form.email, form.username, form.password)).thenReturn(Completable.complete())
                    presenter.register(form)
                }

                it("signs user up") {
                    verify(signUp, atLeastOnce()).invoke(any(), any(), any())
                }

                it("notifies view") {
                    verify(view, atLeastOnce()).successfulSignUp()
                }
            }

        }
    }

})