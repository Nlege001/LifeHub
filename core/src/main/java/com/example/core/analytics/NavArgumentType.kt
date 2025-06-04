package com.example.core.analytics

enum class NavArgumentType(val label: String) {
    EMAIL(label = "email"),
    FIRST_NAME(label = "firstName"),
    LAST_NAME(label = "lastName"),
    PIN(label = "pin"),
    URL(label = "url"),
    ID(label = "id");

    companion object {
        fun NavArgumentType.asPlaceholder(): String = "{${this.label}}"
    }
}