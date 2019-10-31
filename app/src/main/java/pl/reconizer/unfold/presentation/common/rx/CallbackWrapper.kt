package pl.reconizer.unfold.presentation.common.rx

import io.reactivex.observers.DisposableObserver
import pl.reconizer.unfold.presentation.common.errorshandlers.ErrorsHandler

abstract class CallbackWrapper<TEntity>(
        private val errorsHandler: ErrorsHandler,
        private val errorNamespace: String = ErrorsHandler.DEFAULT_ERROR_NAMESPACE
) : DisposableObserver<TEntity>() {

    abstract override fun onComplete()

    abstract override fun onNext(t: TEntity)

    override fun onError(e: Throwable) {
        errorsHandler.onError(e, errorNamespace)
    }

}