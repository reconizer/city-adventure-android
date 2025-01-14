package pl.reconizer.unfold.presentation.menu

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.domain.repositories.IUserRepository
import pl.reconizer.unfold.domain.usecases.authentication.Logout
import pl.reconizer.unfold.presentation.errorhandlers.ErrorsHandler

class MenuPresenterSpec : Spek({

    describe("RegistrationPresenter") {
        lateinit var presenter: MenuPresenter
        lateinit var view: IMenuView
        lateinit var userRepository: IUserRepository
        lateinit var logoutUsecase: Logout
        lateinit var errorsHandler: ErrorsHandler<Error>

        beforeEachTest {
            view = mock()
            userRepository = mock()
            logoutUsecase = mock()
            errorsHandler = mock()
            presenter = MenuPresenter(
                    Schedulers.trampoline(),
                    Schedulers.trampoline(),
                    userRepository,
                    logoutUsecase,
                    errorsHandler
            )
            whenever(logoutUsecase.invoke()).thenReturn(Completable.complete())
        }

        describe("subscribe") {


            it("sets view for error handler") {
                presenter.subscribe(view)
                verify(errorsHandler, atLeastOnce()).view = any()
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

            it("shows and hides loader") {
                presenter.subscribe(view)
                presenter.logout()
                verify(view, atLeastOnce()).showLoader()
                verify(view, atLeastOnce()).hideLoader()
            }

            it("notifies view about successful logout") {
                presenter.subscribe(view)
                presenter.logout()
                verify(logoutUsecase, atLeastOnce()).invoke()
                verify(view, atLeastOnce()).successfulLogout()
            }

        }

    }

})