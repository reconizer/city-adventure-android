package pl.reconizer.unfold.di.modules

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import pl.reconizer.unfold.di.scopes.ViewScope
import pl.reconizer.unfold.presentation.common.errorshandlers.ErrorMessageParser
import pl.reconizer.unfold.presentation.common.errorshandlers.ErrorsHandler
import pl.reconizer.unfold.presentation.common.errorshandlers.IErrorsParser

@Module
class ErrorsHandlersModule {

    @Provides
    @ViewScope
    fun provideErrorsParser(context: Context): IErrorsParser {
        return ErrorMessageParser(context)
    }

    @Provides
    @ViewScope
    fun provideBasicErrorsHandler(
            gson: Gson,
            errorsParser: IErrorsParser
    ): ErrorsHandler {
        return ErrorsHandler(
                gson,
                errorsParser
        )
    }

}