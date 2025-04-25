package com.example.core.utils

object InputValidator {

    const val PASSWORD_LENGTH = 8

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordValid(password: String): Boolean {
        return password.length >= PASSWORD_LENGTH
    }

    fun hasAtLeastOneSpecialCharacter(value: String): Boolean {
        return value.contains(Regex("[^A-Za-z0-9]"))
    }

}