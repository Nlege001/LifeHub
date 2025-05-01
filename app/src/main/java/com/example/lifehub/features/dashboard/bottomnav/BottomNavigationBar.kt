package com.example.lifehub.features.dashboard.bottomnav

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.core.values.Colors
import com.example.core.values.Dimens.pd24
import com.example.lifehub.features.dashboard.bottomnav.BottomNavItem.Companion.getIcon

@Composable
fun BottomNavigationBar(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color.Black
    ) {
        BottomNavItem.getNavItems().forEach { item ->
            val selected = currentRoute == item.page.route
            val label = stringResource(item.title)

            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (currentRoute != item.page.route) {
                        navController.navigate(item.page.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(item.getIcon(selected)),
                        contentDescription = label,
                        modifier = Modifier.size(pd24)
                    )
                },
                label = {
                    Text(
                        label,
                        color = if (selected) Color.White else Color.Gray
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Colors.Lavender,
                    indicatorColor = Colors.Charcoal
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBottomNavigationBar() {
    BottomNavigationBar(rememberNavController())
}
