package pl.reconizer.unfold.di.modules

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import pl.reconizer.unfold.data.entities.Error
import pl.reconizer.unfold.di.scopes.ViewScope
import pl.reconizer.unfold.presentation.errorhandlers.ErrorHandler

@Module
class ErrorHandlersModule {

    @Provides
    @ViewScope
    fun provideBasicErrorHandler(gson: Gson): ErrorHandler<Error> {
        return ErrorHandler.build(gson)
    }

}