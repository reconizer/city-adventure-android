package pl.reconizer.unfold.presentation.common.rx

import io.reactivex.observers.DisposableObserver
import pl.reconizer.unfold.presentation.errorhandlers.ErrorsHandler

abstract class CallbackWrapper<TEntity, TErrorEntity>(private val errorsHandler: ErrorsHandler<TErrorEntity>) : DisposableObserver<TEntity>() {

    abstract override fun onComplete()

    abstract override fun onNext(t: TEntity)

    override fun onError(e: Throwable) {
        errorsHandler.onError(e)
    }

}