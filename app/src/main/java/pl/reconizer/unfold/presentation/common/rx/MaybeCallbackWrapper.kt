package pl.reconizer.unfold.presentation.common.rx

import io.reactivex.observers.DisposableMaybeObserver
import pl.reconizer.unfold.presentation.common.errorshandlers.ErrorsHandler

abstract class MaybeCallbackWrapper<TEntity>(
        private val errorsHandler: ErrorsHandler,
        private val errorNamespace: String = ErrorsHandler.DEFAULT_ERROR_NAMESPACE
) : DisposableMaybeObserver<TEntity>() {

    abstract override fun onSuccess(t: TEntity)

    abstract override fun onComplete()

    override fun onError(e: Throwable) {
        errorsHandler.onError(e, errorNamespace)
    }
}