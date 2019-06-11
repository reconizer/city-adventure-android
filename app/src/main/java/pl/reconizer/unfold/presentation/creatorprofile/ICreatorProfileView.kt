package pl.reconizer.unfold.presentation.creatorprofile

import pl.reconizer.unfold.domain.entities.CreatorAdventure
import pl.reconizer.unfold.presentation.mvp.IListView

interface ICreatorProfileView : IListView<CreatorAdventure> {

    fun showProfile()

}