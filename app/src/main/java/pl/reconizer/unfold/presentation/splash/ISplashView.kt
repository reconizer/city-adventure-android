package pl.reconizer.unfold.presentation.splash

import pl.reconizer.unfold.presentation.mvp.IView

interface ISplashView : IView {

    fun authenticatedUser()

    fun unauthenticatedUser()

}