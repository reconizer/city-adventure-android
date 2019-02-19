package pl.reconizer.unfold.presentation.mvp

import pl.reconizer.unfold.data.entities.Error

interface IView {

    fun showGenericError()
    fun showConnectionError()
    fun showAuthorizationError()
    fun showParametrizedError(errorEntity: Error)
    fun showServerError()

    fun showLoader()
    fun hideLoader()

}