package pl.reconizer.unfold.presentation.common.rx

import io.reactivex.observers.DisposableCompletableObserver
import pl.reconizer.unfold.presentation.common.errorshandlers.ErrorsHandler

abstract class CompletableCallbackWrapper(
        private val errorsHandler: ErrorsHandler,
        private val errorNamespace: String = ErrorsHandler.DEFAULT_ERROR_NAMESPACE
) : DisposableCompletableObserver() {

    abstract override fun onComplete()

    override fun onError(e: Throwable) {
        errorsHandler.onError(e, errorNamespace)
    }

}