package pl.reconizer.cityadventure.presentation.mvp

import com.winterbe.expekt.expect
import org.mockito.Mockito
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class BasePresenterTest : Spek({

    describe("BasePresenter") {
        lateinit var presenter: BasePresenter<IView>
        lateinit var view: IView

        beforeEachTest {
            presenter = BasePresenter()
            view = Mockito.mock(IView::class.java)
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