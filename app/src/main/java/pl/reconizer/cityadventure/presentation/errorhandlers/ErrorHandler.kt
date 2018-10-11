package pl.reconizer.cityadventure.presentation.errorhandlers

import com.google.gson.Gson
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.presentation.mvp.IView
import retrofit2.HttpException
import java.io.IOException
import java.lang.ref.WeakReference

open class ErrorHandler<TErrorEntity>(
    private val gson: Gson,
    private val errorEntityType: Class<TErrorEntity>
) {

    companion object {
        inline fun <reified T> build(gson: Gson): ErrorHandler<T> {
            return ErrorHandler(gson, T::class.java)
        }
    }

    var view: WeakReference<IView>? = null

    open fun onError(e: Throwable) {
        e.printStackTrace()
        when(e) {
            is HttpException -> handleHttpError(e)
            is IOException -> handleNetworkError()
            else -> handleUnknownError(e)
        }
    }

    /**
     * Unhandled status or any other unhandled error
     */
    open fun handleUnknownError(e: Throwable) {
        view?.get()?.showGenericError()
    }

    /**
     * This is a client side error, ex. lost internet connection.
     */
    open fun handleNetworkError() {
        view?.get()?.showConnectionError()
    }

    /**
     * Status 401
     */
    open fun handleUnauthorizedError() {
        view?.get()?.showAuthorizationError()
    }

    /**
     * Status 422
     */
    open fun handleUnprocessableEntityError(e: HttpException) {
        val stringResponse = e.response().errorBody()?.string()
        val entityResponse = try {
            gson.fromJson(stringResponse, errorEntityType)
        } catch (exception: IllegalStateException) {
            gson.fromJson("{}", errorEntityType) // it will create empty instance
        }
        view?.get()?.showParametrizedError(entityResponse as Error)
    }

    /**
     * Status 500
     */
    open fun handleServerError() {
        view?.get()?.showServerError()
    }

    private fun handleHttpError(e: HttpException) {
        when(e.code()) {
            401 -> handleUnauthorizedError()
            422 -> handleUnprocessableEntityError(e)
            in 500..599 -> handleServerError()
            else -> handleUnknownError(e)
        }
    }

}