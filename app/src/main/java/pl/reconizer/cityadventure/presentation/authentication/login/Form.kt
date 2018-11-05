package pl.reconizer.cityadventure.presentation.authentication.login

class Form(val email: String, val password: String) {

    fun isValid(): Boolean {
        return email.isNotBlank() && password.isNotBlank()
    }

}