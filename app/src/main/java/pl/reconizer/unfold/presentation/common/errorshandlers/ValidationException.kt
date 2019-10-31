package pl.reconizer.unfold.presentation.common.errorshandlers

import pl.reconizer.unfold.domain.entities.errors.RawErrors

class ValidationException(val errors: RawErrors) : Exception("Validation exception")