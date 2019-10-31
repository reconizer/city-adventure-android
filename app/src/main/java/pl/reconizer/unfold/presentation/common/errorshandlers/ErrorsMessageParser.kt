package pl.reconizer.unfold.presentation.common.errorshandlers

import android.content.Context
import pl.reconizer.unfold.R
import pl.reconizer.unfold.common.extensions.getStringByNameBang
import pl.reconizer.unfold.domain.entities.errors.ErrorsContainer

class ErrorMessageParser(private val context: Context) : IErrorsParser {

    override fun parse(errorsContainer: ErrorsContainer): String? {
        val messages = mutableListOf<String>()
        val unknownCodes = mutableListOf<String>()
        errorsContainer.rawErrors?.forEach { (key, errorCodes) ->
            errorCodes.forEach { errorCode ->
                val fullErrorCode = "${errorsContainer.errorsNamespace}_${key}_$errorCode"
                try {
                    messages.add(context.resources.getStringByNameBang(context, fullErrorCode))
                } catch (e: IllegalArgumentException) {
                    unknownCodes.add(fullErrorCode)
                }
            }
        }
        return if (messages.isEmpty() && unknownCodes.isEmpty()) {
            null
        } else {
            var message = messages.joinToString(" ")
            if (unknownCodes.isNotEmpty()) {
                if (message.isNullOrBlank()) {
                    message = "\n"
                }
                message += "${context.resources.getString(R.string.error_unknown_codes)} ${unknownCodes.joinToString(", ")}"
            }
            message
        }
    }

}