package pl.reconizer.cityadventure.presentation.adventure.startpoint

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.entities.AdventureStartPoint
import pl.reconizer.cityadventure.domain.usecases.adventure.GetAdventureStartPoint
import pl.reconizer.cityadventure.presentation.errorhandlers.ErrorHandler

class StartPointPresenterSpek : Spek({

    describe("StartPointPresenter") {
        lateinit var presenter: StartPointPresenter
        lateinit var view: IStartPointView
        lateinit var getAdventureStartPoint: GetAdventureStartPoint
        lateinit var errorHandler: ErrorHandler<Error>

        var adventureStartPoint = mock<AdventureStartPoint>()
        var adventure = mock<Adventure>()

        before { whenever(adventure.adventureId).thenReturn("test-id") }

        beforeEachTest {
            view = mock()
            getAdventureStartPoint = mock()
            errorHandler = mock()
            presenter = StartPointPresenter(
                    Schedulers.trampoline(),
                    Schedulers.trampoline(),
                    getAdventureStartPoint,
                    errorHandler,
                    adventure
            )
            whenever(getAdventureStartPoint.invoke(any())).thenReturn(Single.just(adventureStartPoint))
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
                verify(getAdventureStartPoint, atLeastOnce()).invoke(any())
                verify(view, atLeastOnce()).show(adventureStartPoint)
            }

        }
    }
})