package pl.reconizer.cityadventure.di.modules

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import pl.reconizer.cityadventure.data.entities.Error
import pl.reconizer.cityadventure.di.scopes.ViewScope
import pl.reconizer.cityadventure.presentation.errorhandlers.ErrorHandler

@Module
class ErrorHandlersModule() {

    @Provides
    @ViewScope
    fun provideBasicErrorHandler(gson: Gson): ErrorHandler<Error> {
        return ErrorHandler.build(gson)
    }

}