package com.example.lifehub.features.auth.signup.data

import com.example.core.utils.InputValidator
import com.example.wpinterviewpractice.R

sealed class PasswordChecks {

    abstract val checkLabel: Int
    abstract fun isValid(password: String): Boolean

    data class PasswordLength(
        override val checkLabel: Int = R.string.sign_up_password_length
    ) : PasswordChecks() {
        override fun isValid(password: String): Boolean {
            return password.length >= InputValidator.PASSWORD_LENGTH
        }
    }

    data class HasOneCapitalLetter(
        override val checkLabel: Int = R.string.sign_up_one_capital_letter
    ) : PasswordChecks() {
        override fun isValid(password: String): Boolean {
            return password.any { it.isUpperCase() }
        }
    }

    data class HasOneSpecialCharacter(
        override val checkLabel: Int = R.string.sign_up_special_character
    ) : PasswordChecks() {
        override fun isValid(password: String): Boolean {
            return InputValidator.hasAtLeastOneSpecialCharacter(password)
        }
    }

    data class HasOneNumber(
        override val checkLabel: Int = R.string.sign_up_one_number
    ) : PasswordChecks() {
        override fun isValid(password: String): Boolean {
            return password.any { it.isDigit() }
        }
    }

    companion object {

        fun getAllChecks(): List<PasswordChecks> {
            return listOf(
                PasswordLength(),
                HasOneCapitalLetter(),
                HasOneNumber(),
                HasOneSpecialCharacter(),
            )
        }
    }

}