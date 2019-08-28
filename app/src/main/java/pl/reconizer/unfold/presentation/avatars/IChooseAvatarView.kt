package pl.reconizer.unfold.presentation.avatars

import pl.reconizer.unfold.presentation.mvp.IView

interface IChooseAvatarView : IView {

    fun showAvatars()

    fun avatarUpdated()

}