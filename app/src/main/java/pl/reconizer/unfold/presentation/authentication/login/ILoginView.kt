package pl.reconizer.unfold.presentation.authentication.login

import pl.reconizer.unfold.presentation.mvp.IView

interface ILoginView : IView {

    fun successfulSignIn()

}