package pl.reconizer.unfold.presentation.adventure.journal

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.domain.entities.Adventure
import pl.reconizer.unfold.domain.entities.AdventurePointWithClues
import pl.reconizer.unfold.domain.entities.AdventureStartPoint
import pl.reconizer.unfold.domain.repositories.IAdventureRepository
import pl.reconizer.unfold.presentation.errorhandlers.ErrorHandler

class JournalPresenterSpec : Spek({

    describe("JournalPresenter") {
        lateinit var presenter: JournalPresenter
        lateinit var view: IJournalView
        lateinit var adventureRepository: IAdventureRepository
        lateinit var errorHandler: ErrorHandler<Error>

        val adventure = mock<Adventure>()
        val adventureStartPoint = mock<AdventureStartPoint>()
        val points = mock<List<AdventurePointWithClues>>()

        before { whenever(adventure.adventureId).thenReturn("test-id") }

        beforeEachTest {
            view = mock()
            adventureRepository = mock()
            errorHandler = mock()
            presenter = JournalPresenter(
                    Schedulers.trampoline(),
                    Schedulers.trampoline(),
                    adventureRepository,
                    errorHandler,
                    adventure,
                    adventureStartPoint
            )
            whenever(adventureRepository.getAdventureDiscoveredClues(any())).thenReturn(Single.just(points))
        }

        afterEachTest {
            presenter.unsubscribe()
        }

        describe("subscribe") {

            it("sets view for error handler") {
                presenter.subscribe(view)
                verify(errorHandler, atLeastOnce()).view = any()
            }

        }

        describe("fetchClues") {

            it("notifies view about fetched clues") {
                presenter.subscribe(view)
                presenter.fetchClues()
                verify(adventureRepository, atLeastOnce()).getAdventureDiscoveredClues(any())
                verify(view, atLeastOnce()).showClues()
            }

        }
    }

})