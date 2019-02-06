package pl.reconizer.cityadventure.presentation.authentication.registration

class Form(
        val email: String,
        val username: String,
        val password: String,
        val passwordConfirmation: String
) {

    fun isValid(): Boolean {
        return email.isNotBlank() &&
                username.isNotBlank() &&
                password.isNotBlank() &&
                passwordConfirmation == password
    }

}