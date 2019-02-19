package pl.reconizer.unfold.presentation.authentication.login

class Form(val email: String, val password: String) {

    fun isValid(): Boolean {
        return email.isNotBlank() && password.isNotBlank()
    }

}