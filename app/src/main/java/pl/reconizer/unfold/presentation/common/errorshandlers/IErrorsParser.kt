package pl.reconizer.unfold.presentation.common.errorshandlers

import pl.reconizer.unfold.domain.entities.errors.ErrorsContainer

interface IErrorsParser {

    fun parse(errorsContainer: ErrorsContainer): String?

}