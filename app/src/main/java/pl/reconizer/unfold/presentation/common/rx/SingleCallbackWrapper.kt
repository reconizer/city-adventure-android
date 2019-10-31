package pl.reconizer.unfold.presentation.common.rx

import io.reactivex.observers.DisposableSingleObserver
import pl.reconizer.unfold.presentation.common.errorshandlers.ErrorsHandler

abstract class SingleCallbackWrapper<TEntity>(
        private val errorsHandler: ErrorsHandler,
        private val errorNamespace: String = ErrorsHandler.DEFAULT_ERROR_NAMESPACE
) : DisposableSingleObserver<TEntity>() {

    abstract override fun onSuccess(t: TEntity)

    override fun onError(e: Throwable) {
        errorsHandler.onError(e, errorNamespace)
    }

}