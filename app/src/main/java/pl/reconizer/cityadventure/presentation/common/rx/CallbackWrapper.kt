package pl.reconizer.cityadventure.presentation.common.rx

import io.reactivex.observers.DisposableObserver
import pl.reconizer.cityadventure.presentation.errorhandlers.ErrorHandler

abstract class CallbackWrapper<TEntity, TErrorEntity>(private val errorHandler: ErrorHandler<TErrorEntity>) : DisposableObserver<TEntity>() {

    abstract override fun onComplete()

    abstract override fun onNext(t: TEntity)

    override fun onError(e: Throwable) {
        errorHandler.onError(e)
    }

}