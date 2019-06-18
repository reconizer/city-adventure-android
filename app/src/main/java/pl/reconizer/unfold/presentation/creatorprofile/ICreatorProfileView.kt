package pl.reconizer.unfold.presentation.creatorprofile

import pl.reconizer.unfold.domain.entities.Adventure
import pl.reconizer.unfold.presentation.mvp.IListView

interface ICreatorProfileView : IListView<Adventure> {

    fun showProfile()

}