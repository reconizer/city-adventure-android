package pl.reconizer.cityadventure.di.modules

import dagger.Module
import dagger.Provides
import pl.reconizer.cityadventure.presentation.map.AdventurePinMapper
import pl.reconizer.cityadventure.presentation.map.IPinMapper
import pl.reconizer.cityadventure.presentation.map.PinProvider
import pl.reconizer.cityadventure.presentation.map.StartedAdventurePinMapper
import javax.inject.Named
import javax.inject.Singleton

@Module
class UtilsModule {

    @Provides
    @Singleton
    fun providePinProvider(): PinProvider {
        return PinProvider()
    }

    @Provides
    @Named("adventure_pin_mapper")
    @Singleton
    fun provideAdventurePinMapper(
            pinProvider: PinProvider
    ): IPinMapper {
        return AdventurePinMapper(pinProvider)
    }

    @Provides
    @Named("started_adventure_pin_mapper")
    @Singleton
    fun provideStartedAdventurePinMapper(
            pinProvider: PinProvider
    ): IPinMapper {
        return StartedAdventurePinMapper(pinProvider)
    }
}