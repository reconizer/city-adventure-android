package pl.reconizer.cityadventure.presentation.authentication.login

import pl.reconizer.cityadventure.common.extensions.isValidAsEmail

data class Form(val email: String?, val password: String?) {

    fun isValid(): Boolean {
        return !email.isNullOrBlank() && !password.isNullOrBlank() && email!!.isValidAsEmail()
    }

}