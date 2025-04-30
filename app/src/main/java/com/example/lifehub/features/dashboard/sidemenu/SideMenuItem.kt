package com.example.lifehub.features.dashboard.sidemenu

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.core.analytics.Page
import com.example.wpinterviewpractice.R

data class SideMenuItem(
    @DrawableRes val icon: Int,
    @StringRes val label: Int,
    val route: String
) {
    companion object {
        fun getSideMenuItems(): List<SideMenuItem> =
            listOf(
                SideMenuItem(
                    icon = R.drawable.ic_journal,
                    label = R.string.journal,
                    route = Page.JOURNAL.route,
                ),
                SideMenuItem(
                    icon = R.drawable.ic_reflections,
                    label = R.string.reflections,
                    route = Page.REFLECTIONS.route,
                ),
                SideMenuItem(
                    icon = R.drawable.ic_insights,
                    label = R.string.insights,
                    route = Page.INSIGHTS.route,
                ),
                SideMenuItem(
                    icon = R.drawable.ic_calendar,
                    label = R.string.calendar,
                    route = Page.CALENDAR.route,
                ),
                SideMenuItem(
                    icon = R.drawable.ic_invite,
                    label = R.string.invite_a_friend,
                    route = Page.INVITE_A_FRIEND.route,
                ),
                SideMenuItem(
                    icon = R.drawable.ic_settings,
                    label = R.string.settings,
                    route = Page.SETTINGS.route,
                ),
                SideMenuItem(
                    icon = R.drawable.ic_sign_out,
                    label = R.string.sign_out,
                    route = Page.SIGN_OUT.route,
                )
            )
    }
}