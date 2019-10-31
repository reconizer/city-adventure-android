package pl.reconizer.unfold.domain.entities.errors

data class ErrorsContainer(
        val errorsNamespace: String,
        val rawErrors: RawErrors?
)