package pl.reconizer.cityadventure.presentation.adventure.journal

import dagger.Subcomponent
import pl.reconizer.cityadventure.di.scopes.ViewScope

@Subcomponent(modules = [
    JournalModule::class
])
@ViewScope
interface JournalComponent {

    fun inject(target: JournalFragment)

}