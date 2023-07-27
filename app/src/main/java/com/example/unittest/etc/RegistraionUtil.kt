package com.example.unittest.etc

object RegistraionUtil {

    private val existsingUsers = listOf("Peter", "Carl")

    /**
     * the input is not valid if...
     * ...the userName/password is empty
     * ...the userName is aleready taken
     * ...the confirmed password is not the smae as the real password
     * ...the password contains less the 2 digits
     */
    fun validateRegistrationInput(
        userName: String,
        password: String,
        confirmedPassword: String
    ): Boolean {
        if (userName.isEmpty() || password.isEmpty()) {
            return false
        }

        if (password != confirmedPassword) {
            return false
        }

        if (userName in existsingUsers) {
            return false
        }

        if (password.count { it.isDigit() } < 2) {
            return false
        }
        return true
    }
}