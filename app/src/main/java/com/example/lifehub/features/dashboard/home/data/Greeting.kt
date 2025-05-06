package com.example.lifehub.features.dashboard.home.data

import androidx.annotation.Keep

@Keep
data class Greeting(
    val message: String = "",
    val timeOfDay: String = "",
    val tag: String = ""
) {
    constructor() : this("", "", "")
    companion object {
        fun getFallBackGreeting(firstName: String?): Greeting {
            val message = if (firstName.isNullOrEmpty()) {
                "Welcome back!"
            } else {
                "Welcome back $firstName!"
            }

            return Greeting(
                message = message,
                timeOfDay = "",
                tag = ""
            )
        }
    }
}