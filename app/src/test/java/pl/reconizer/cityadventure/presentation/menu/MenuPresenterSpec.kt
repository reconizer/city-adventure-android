package pl.reconizer.cityadventure.presentation.menu

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.domain.repositories.IUserRepository
import pl.reconizer.cityadventure.domain.usecases.authentication.Logout
import pl.reconizer.cityadventure.domain.usecases.authentication.SignUp
import pl.reconizer.cityadventure.presentation.errorhandlers.ErrorHandler

class MenuPresenterSpec : Spek({

    describe("RegistrationPresenter") {
        lateinit var presenter: MenuPresenter
        lateinit var view: IMenuView
        lateinit var userRepository: IUserRepository
        lateinit var logoutUsecase: Logout
        lateinit var errorHandler: ErrorHandler<Error>

        beforeEachTest {
            view = mock()
            userRepository = mock()
            logoutUsecase = mock()
            errorHandler = mock()
            presenter = MenuPresenter(
                    Schedulers.trampoline(),
                    Schedulers.trampoline(),
                    userRepository,
                    logoutUsecase,
                    errorHandler
            )
            whenever(logoutUsecase.invoke()).thenReturn(Completable.complete())
        }

        describe("subscribe") {


            it("sets view for error handler") {
                presenter.subscribe(view)
                verify(errorHandler, atLeastOnce()).view = any()
            }

        }

        describe("fetchProfile") {

            it("notifies view about fetched profile data") {
                presenter.subscribe(view)
                presenter.fetchProfile()
                verify(view, atLeastOnce()).showProfile()
            }

        }

        describe("logout") {

            it("notifies view about successful logout") {
                presenter.subscribe(view)
                presenter.logout()
                verify(logoutUsecase, atLeastOnce()).invoke()
                verify(view, atLeastOnce()).successfulLogout()
            }

        }

    }

})