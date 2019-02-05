package pl.reconizer.cityadventure.presentation.authentication.registration

class Form(val email: String, val password: String, val passwordConfirmation: String) {

    fun isValid(): Boolean {
        return email.isNotBlank() &&
                password.isNotBlank() &&
                passwordConfirmation == password
    }

}