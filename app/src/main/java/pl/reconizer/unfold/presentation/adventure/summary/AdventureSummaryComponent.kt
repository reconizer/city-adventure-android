package pl.reconizer.unfold.presentation.adventure.summary

import dagger.Subcomponent
import pl.reconizer.unfold.di.scopes.ViewScope

@Subcomponent(modules = [
    AdventureSummaryModule::class
])
@ViewScope
interface AdventureSummaryComponent {

    fun inject(target: AdventureSummaryFragment)

}