package pl.reconizer.unfold.presentation.common.rx

import io.reactivex.observers.DisposableCompletableObserver
import pl.reconizer.unfold.presentation.errorhandlers.ErrorsHandler

abstract class CompletableCallbackWrapper<TErrorEntity>(private val errorsHandler: ErrorsHandler<TErrorEntity>) : DisposableCompletableObserver() {

    abstract override fun onComplete()

    override fun onError(e: Throwable) {
        errorsHandler.onError(e)
    }

}