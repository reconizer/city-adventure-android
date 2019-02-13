package pl.reconizer.unfold.presentation.menu

import pl.reconizer.unfold.presentation.mvp.IView

interface IMenuView : IView {

    fun showProfile()

    fun successfulLogout()

}