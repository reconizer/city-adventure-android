package pl.reconizer.cityadventure.presentation.mvp

import com.winterbe.expekt.expect
import io.reactivex.disposables.CompositeDisposable
import org.mockito.Mockito
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class BasePresenterSpec : Spek({

    describe("BasePresenter") {
        lateinit var presenter: BasePresenter<IView>
        lateinit var view: IView
        lateinit var disposables: CompositeDisposable

        beforeEachTest {
            presenter = BasePresenter()
            view = Mockito.mock(IView::class.java)
            disposables = Mockito.spy(CompositeDisposable::class.java)
            presenter.disposables = disposables
        }

        describe("subscribe") {

            it("disposes all subscribers") {
                presenter.subscribe(view)
                Mockito.verify(disposables, Mockito.atLeastOnce()).clear()
                Mockito.verify(disposables, Mockito.atLeastOnce()).dispose()
            }

            it("saves the view") {
                presenter.subscribe(view)
                expect(presenter.view).to.be.equal(view)
            }
        }

        describe("unsubscribe") {

            it("removes a reference to the view") {
                presenter.unsubscribe()
                expect(presenter.view).to.be.`null`
            }

            it("disposes all subscribers") {
                presenter.subscribe(view)
                Mockito.verify(disposables, Mockito.atLeastOnce()).clear()
                Mockito.verify(disposables, Mockito.atLeastOnce()).dispose()
            }
        }

        describe("isSubscribed") {

            context("when subscribed") {
                beforeEachTest { presenter.subscribe(view) }

                it("is true") {
                    expect(presenter.isSubscribed).to.be.`true`
                }
            }

            context("when unsubscribed") {
                beforeEachTest { presenter.unsubscribe() }

                it("is false") {
                    expect(presenter.isSubscribed).to.be.`false`
                }
            }
        }
    }

})