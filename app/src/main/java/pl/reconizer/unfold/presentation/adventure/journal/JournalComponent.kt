package pl.reconizer.unfold.presentation.adventure.journal

import dagger.Subcomponent
import pl.reconizer.unfold.di.scopes.ViewScope

@Subcomponent(modules = [
    JournalModule::class
])
@ViewScope
interface JournalComponent {

    fun inject(target: JournalFragment)

}