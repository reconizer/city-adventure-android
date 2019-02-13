package pl.reconizer.unfold.presentation.common.rx

import io.reactivex.observers.DisposableCompletableObserver
import pl.reconizer.unfold.presentation.errorhandlers.ErrorHandler

abstract class CompletableCallbackWrapper<TErrorEntity>(private val errorHandler: ErrorHandler<TErrorEntity>) : DisposableCompletableObserver() {

    abstract override fun onComplete()

    override fun onError(e: Throwable) {
        errorHandler.onError(e)
    }

}