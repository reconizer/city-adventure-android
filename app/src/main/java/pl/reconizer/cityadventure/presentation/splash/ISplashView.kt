package pl.reconizer.cityadventure.presentation.splash

import pl.reconizer.cityadventure.presentation.mvp.IView

interface ISplashView : IView {

    fun authenticatedUser()

    fun unauthenticatedUser()

}