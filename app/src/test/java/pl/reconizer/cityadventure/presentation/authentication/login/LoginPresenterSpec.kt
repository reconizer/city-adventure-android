package pl.reconizer.cityadventure.presentation.authentication.login

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.domain.usecases.authentication.SignIn
import pl.reconizer.cityadventure.presentation.errorhandlers.ErrorHandler

class LoginPresenterSpec : Spek({

    describe("LoginPresenter") {
        val view = mock<ILoginView>()
        val scheduler = Schedulers.trampoline()
        val errorHandler = mock<ErrorHandler<Error>>()
        val signIn = mock<SignIn>()

        val presenter = LoginPresenter(scheduler, signIn, errorHandler)

        beforeEachTest {
            reset(view, errorHandler, signIn)
        }

        describe("subscribe") {


            it("sets view for error handler") {
                presenter.subscribe(view)
                verify(errorHandler, atLeastOnce()).view = any()
            }

        }

        describe("login") {

            context("when form is valid") {
                val form = Form(
                        email = "test@test.com",
                        password = "test"
                )

                beforeEachTest {
                    whenever(signIn.invoke(form.email, form.password)).thenReturn(Completable.complete())
                    presenter.login(form)
                }

                it("signs user in") {
                    verify(signIn, atLeastOnce()).invoke(any(), any())
                }

                it("notifies view") {
                    verify(view, atLeastOnce()).successfulSignIn()
                }
            }

        }
    }

})