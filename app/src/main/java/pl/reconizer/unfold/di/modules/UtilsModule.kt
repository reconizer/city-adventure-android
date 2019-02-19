package pl.reconizer.unfold.di.modules

import dagger.Module
import dagger.Provides
import pl.reconizer.unfold.presentation.map.AdventurePinMapper
import pl.reconizer.unfold.presentation.map.IPinMapper
import pl.reconizer.unfold.presentation.map.PinProvider
import pl.reconizer.unfold.presentation.map.StartedAdventurePinMapper
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