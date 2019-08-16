package pl.reconizer.unfold.presentation.common.rx

import io.reactivex.observers.DisposableMaybeObserver
import pl.reconizer.unfold.presentation.errorhandlers.ErrorsHandler

abstract class MaybeCallbackWrapper<TEntity, TErrorEntity>(
        private val errorsHandler: ErrorsHandler<TErrorEntity>) : DisposableMaybeObserver<TEntity>() {

    abstract override fun onSuccess(t: TEntity)

    abstract override fun onComplete()

    override fun onError(e: Throwable) {
        errorsHandler.onError(e)
    }
}