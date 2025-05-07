package com.example.core.analytics

enum class NavFlows(val route: String) {
    AUTH(route = "AUTH"),
    QUESTIONAIRE(route = "QUESTIONAIRE"),
    BOTTOM_NAV(route = "BOTTOM_NAV"),
    MAIN("MAIN");

    companion object {
        fun getFlowFromRoute(route: String?): NavFlows {
            return NavFlows.entries.find { it.route == route } ?: AUTH
        }
    }

}