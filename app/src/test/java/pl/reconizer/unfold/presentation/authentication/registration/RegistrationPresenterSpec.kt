package pl.reconizer.unfold.presentation.authentication.registration

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.domain.usecases.authentication.SignUp
import pl.reconizer.unfold.presentation.errorhandlers.ErrorsHandler

class RegistrationPresenterSpec : Spek({

    describe("RegistrationPresenter") {
        val view = mock<IRegistrationView>()
        val scheduler = Schedulers.trampoline()
        val errorHandler = mock<ErrorsHandler<Error>>()
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

                it("shows and hides loader") {
                    verify(view, atLeastOnce()).showLoader()
                    verify(view, atLeastOnce()).hideLoader()
                }

                it("signs user up") {
                    verify(signUp, atLeastOnce()).invoke(form.email, form.username, form.password)
                }

                it("notifies view") {
                    verify(view, atLeastOnce()).successfulSignUp()
                }
            }

        }
    }

})