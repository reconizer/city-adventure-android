package pl.reconizer.unfold.presentation.userprofile

import pl.reconizer.unfold.domain.entities.UserAdventure
import pl.reconizer.unfold.presentation.mvp.IListView

interface IUserProfileView : IListView<UserAdventure> {

    fun showProfile()

}