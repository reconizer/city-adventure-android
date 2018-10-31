package pl.reconizer.cityadventure.presentation.mvp

import pl.reconizer.cityadventure.data.entities.Error

interface IView {

    fun showGenericError()
    fun showConnectionError()
    fun showAuthorizationError()
    fun showParametrizedError(errorEntity: Error)
    fun showServerError()

}