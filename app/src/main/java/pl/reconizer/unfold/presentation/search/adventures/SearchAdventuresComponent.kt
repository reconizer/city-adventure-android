package pl.reconizer.unfold.presentation.search.adventures

import dagger.Subcomponent
import pl.reconizer.unfold.di.scopes.ViewScope

@Subcomponent(modules = [
    SearchAdventuresModule::class
])
@ViewScope
interface SearchAdventuresComponent {

    fun inject(target: AdventuresFragmentPage)

}