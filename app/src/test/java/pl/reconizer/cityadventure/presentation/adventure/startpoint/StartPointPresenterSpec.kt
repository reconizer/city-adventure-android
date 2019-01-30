package pl.reconizer.cityadventure.presentation.adventure.startpoint

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.data.repositories.AdventureRepository
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.entities.AdventureStartPoint
import pl.reconizer.cityadventure.presentation.errorhandlers.ErrorHandler

class StartPointPresenterSpec : Spek({

    describe("StartPointPresenter") {
        lateinit var presenter: StartPointPresenter
        lateinit var view: IStartPointView
        lateinit var adventureRepository: AdventureRepository
        lateinit var errorHandler: ErrorHandler<Error>

        var adventureStartPoint = mock<AdventureStartPoint>()
        var adventure = mock<Adventure>()

        before { whenever(adventure.adventureId).thenReturn("test-id") }

        beforeEachTest {
            view = mock()
            adventureRepository = mock()
            errorHandler = mock()
            presenter = StartPointPresenter(
                    Schedulers.trampoline(),
                    Schedulers.trampoline(),
                    adventureRepository,
                    errorHandler,
                    adventure
            )
            whenever(adventureRepository.getAdventure(any())).thenReturn(Single.just(adventureStartPoint))
            whenever(adventureRepository.startAdventure(any())).thenReturn(Completable.complete())
        }

        afterEachTest { presenter.unsubscribe() }

        describe("subscribe") {

            it("sets view for error handler") {
                presenter.subscribe(view)
                verify(errorHandler, atLeastOnce()).view = any()
            }

        }

        describe("fetchData") {

            it("notifies view about fetched start point data") {
                presenter.subscribe(view)
                presenter.fetchData()
                verify(adventureRepository, atLeastOnce()).getAdventure(any())
                verify(view, atLeastOnce()).show(adventureStartPoint)
            }

        }

        describe("startAdventure") {

            it("notifies view about successfully started adventure") {
                presenter.subscribe(view)
                presenter.startAdventure()
                verify(adventureRepository, atLeastOnce()).startAdventure(any())
                verify(view, atLeastOnce()).adventureStarted()
            }

        }
    }
})