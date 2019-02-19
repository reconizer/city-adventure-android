package pl.reconizer.unfold.presentation.common.rx

import io.reactivex.observers.DisposableSingleObserver
import pl.reconizer.unfold.presentation.errorhandlers.ErrorHandler

abstract class SingleCallbackWrapper<TEntity, TErrorEntity>(private val errorHandler: ErrorHandler<TErrorEntity>) : DisposableSingleObserver<TEntity>() {

    abstract override fun onSuccess(t: TEntity)

    override fun onError(e: Throwable) {
        errorHandler.onError(e)
    }

}