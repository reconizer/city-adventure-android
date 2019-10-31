package pl.reconizer.unfold.presentation.mvp

import pl.reconizer.unfold.domain.entities.errors.ErrorsContainer

interface IView {

    fun showGenericError()
    fun showConnectionError()
    fun showAuthorizationError()
    fun showParametrizedError(errors: ErrorsContainer, translatedErrorMessage: String?)
    fun showServerError()

    fun showLoader()
    fun hideLoader()

}