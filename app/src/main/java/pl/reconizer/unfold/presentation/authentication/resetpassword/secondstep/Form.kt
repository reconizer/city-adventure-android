package pl.reconizer.unfold.presentation.authentication.resetpassword.secondstep

data class Form(
        val code: String,
        val password: String,
        val passwordConfirmation: String
) {

    fun isValid(): Boolean {
        return code.isNotBlank() &&
                password.isNotBlank() &&
                password == passwordConfirmation
    }

}