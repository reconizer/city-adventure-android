package pl.reconizer.unfold.presentation.common.errorshandlers

import com.google.gson.Gson
import pl.reconizer.unfold.common.extensions.fromJson
import pl.reconizer.unfold.domain.entities.errors.ErrorsContainer
import pl.reconizer.unfold.domain.entities.errors.RawErrors
import pl.reconizer.unfold.presentation.mvp.IView
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.lang.ref.WeakReference
import javax.inject.Inject

open class ErrorsHandler @Inject constructor(
        private val gson: Gson,
        private val errorsParser: IErrorsParser
) {

    var view: WeakReference<IView>? = null

    open fun onError(e: Throwable, namespace: String) {
        e.printStackTrace()
        when(e) {
            is ValidationException -> handleValidationError(e, namespace)
            is HttpException -> handleHttpError(e, namespace)
            is IOException -> handleNetworkError()
            else -> handleUnknownError(e)
        }
    }

    /**
     * Unhandled status or any other unhandled error
     */
    open fun handleUnknownError(e: Throwable) {
        Timber.e(e)
        view?.get()?.showGenericError()
    }

    open fun handleValidationError(e: ValidationException, errorNamespace: String) {
        val errorsContainer = ErrorsContainer(
                errorsNamespace  = errorNamespace,
                rawErrors = e.errors
        )
        view?.get()?.showParametrizedError(errorsContainer, errorsParser.parse(errorsContainer))
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
    open fun handleUnprocessableEntityError(e: HttpException, errorNamespace: String) {
        Timber.e(e)
        val stringResponse = e.response().errorBody()?.string() ?: ""
        val errorsContainer = ErrorsContainer(
                errorsNamespace = errorNamespace,
                rawErrors = try {
                    gson.fromJson<RawErrors>(stringResponse) ?: null
                } catch (exception: Exception) {
                    null
                }
        )
        view?.get()?.showParametrizedError(errorsContainer, errorsParser.parse(errorsContainer))
    }

    /**
     * Status 404
     */
    open fun handleNotFoundError(e: HttpException, errorNamespace: String) {
        val errorsContainer = ErrorsContainer(
                errorsNamespace = errorNamespace,
                rawErrors = RawErrors().apply {
                    this["base"] = listOf("not_found")
                }
        )
        view?.get()?.showParametrizedError(errorsContainer, errorsParser.parse(errorsContainer))
    }

    /**
     * Status 5xx
     */
    open fun handleServerError() {
        view?.get()?.showServerError()
    }

    private fun handleHttpError(e: HttpException, errorNamespace: String) {
        when(e.code()) {
            401 -> handleUnauthorizedError()
            404 -> handleNotFoundError(e, errorNamespace)
            422 -> handleUnprocessableEntityError(e, errorNamespace)
            in 500..599 -> handleServerError()
            else -> handleUnknownError(e)
        }
    }

    companion object {
        const val DEFAULT_ERROR_NAMESPACE = "error"
    }

}