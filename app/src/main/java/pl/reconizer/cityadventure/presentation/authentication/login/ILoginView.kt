package pl.reconizer.cityadventure.presentation.authentication.login

import pl.reconizer.cityadventure.presentation.mvp.IView

interface ILoginView : IView {

    fun successfulSignIn()

}