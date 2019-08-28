package pl.reconizer.unfold.presentation.common.rx

import io.reactivex.observers.DisposableSingleObserver
import pl.reconizer.unfold.presentation.errorhandlers.ErrorsHandler

abstract class SingleCallbackWrapper<TEntity, TErrorEntity>(private val errorsHandler: ErrorsHandler<TErrorEntity>) : DisposableSingleObserver<TEntity>() {

    abstract override fun onSuccess(t: TEntity)

    override fun onError(e: Throwable) {
        errorsHandler.onError(e)
    }

}