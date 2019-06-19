package pl.reconizer.unfold.presentation.search.creators

import dagger.Subcomponent
import pl.reconizer.unfold.di.scopes.ViewScope

@Subcomponent(modules = [
    SearchCreatorsModule::class
])
@ViewScope
interface SearchCreatorsComponent {

    fun inject(target: CreatorsFragmentPage)

}