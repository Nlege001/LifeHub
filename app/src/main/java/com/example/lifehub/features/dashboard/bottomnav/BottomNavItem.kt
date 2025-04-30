package com.example.lifehub.features.dashboard.bottomnav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.core.analytics.Page
import com.example.wpinterviewpractice.R

data class BottomNavItem(
    @StringRes val title: Int,
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unSelectedIcon: Int,
    val page: Page,
) {

    companion object {

        fun getNavItems(): List<BottomNavItem> {
            return listOf(
                BottomNavItem(
                    title = R.string.home,
                    selectedIcon = R.drawable.ic_home_selected,
                    unSelectedIcon = R.drawable.ic_home_unselected,
                    page = Page.DASHBOARD_HOME,
                ),
                BottomNavItem(
                    title = R.string.posts,
                    selectedIcon = R.drawable.ic_posts_selected,
                    unSelectedIcon = R.drawable.ic_posts_unselected,
                    page = Page.POSTS,
                ),
                BottomNavItem(
                    title = R.string.progress,
                    selectedIcon = R.drawable.ic_progress_selected,
                    unSelectedIcon = R.drawable.ic_progress_unselected,
                    page = Page.PROGRESS,
                ),
                BottomNavItem(
                    title = R.string.messages,
                    selectedIcon = R.drawable.ic_messages_selected,
                    unSelectedIcon = R.drawable.ic_messages_unselected,
                    page = Page.MESSAGES,
                ),
                BottomNavItem(
                    title = R.string.profile,
                    selectedIcon = R.drawable.ic_profile_selected,
                    unSelectedIcon = R.drawable.ic_profile_unselected,
                    page = Page.PROFILE,
                ),
            )
        }

        fun BottomNavItem.getIcon(isSelected: Boolean): Int {
            return if (isSelected) {
                this.selectedIcon
            } else {
                this.unSelectedIcon
            }
        }
    }
}