package pl.reconizer.unfold.presentation.mvp

import io.reactivex.disposables.CompositeDisposable

open class BasePresenter<T : IView> {

    var disposables: CompositeDisposable = CompositeDisposable()

    var view: T? = null
        private set

    val isSubscribed: Boolean
        get() = view != null

    open fun subscribe(view: T) {
        disposables.apply {
            clear()
            dispose()
        }
        disposables = CompositeDisposable()

        this.view = view
    }

    open fun unsubscribe() {
        view = null
        disposables.apply {
            clear()
            dispose()
        }
    }

}