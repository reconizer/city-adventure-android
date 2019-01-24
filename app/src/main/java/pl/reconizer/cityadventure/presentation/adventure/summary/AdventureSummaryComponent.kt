package pl.reconizer.cityadventure.presentation.adventure.summary

import dagger.Subcomponent
import pl.reconizer.cityadventure.di.scopes.ViewScope

@Subcomponent(modules = [
    AdventureSummaryModule::class
])
@ViewScope
interface AdventureSummaryComponent {

    fun inject(target: AdventureSummaryFragment)

}