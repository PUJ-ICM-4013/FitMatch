package com.example.fitmatch.presentation.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fitmatch.presentation.ui.components.BottomNavItem
import com.example.fitmatch.presentation.ui.components.BottomNavigationBar
import com.example.fitmatch.presentation.ui.screens.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomNavItems = listOf(
        BottomNavItem(
            route = NavigationRoutes.HOME,
            icon = Icons.Default.Home,
            label = "Home"
        ),
        BottomNavItem(
            route = NavigationRoutes.SEARCH,
            icon = Icons.Default.Search,
            label = "Search"
        ),
        BottomNavItem(
            route = NavigationRoutes.INBOX,
            icon = Icons.Default.Email,
            label = "Inbox"
        ),
        BottomNavItem(
            route = NavigationRoutes.NOTIFICATIONS,
            icon = Icons.Default.Notifications,
            label = "Notifications",
            badgeCount = 5 // Ejemplo de badge con notificaciones
        ),
        BottomNavItem(
            route = NavigationRoutes.PROFILE,
            icon = Icons.Default.Person,
            label = "Profile",
            isProfile = true
        )
    )

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                items = bottomNavItems,
                currentRoute = currentDestination?.route ?: NavigationRoutes.HOME,
                onItemClick = { route ->
                    navController.navigate(route) {
                        // Evita múltiples copias de la misma pantalla en el stack
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Evita recomposiciones innecesarias
                        launchSingleTop = true
                        // Restaura el estado cuando navegamos de vuelta
                        restoreState = true
                    }
                },
                profileImageUrl = "https://example.com/profile.jpg" // URL de ejemplo
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = NavigationRoutes.HOME,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(NavigationRoutes.HOME) {
                HomeScreen()
            }
            composable(NavigationRoutes.SEARCH) {
                SearchScreen()
            }
            composable(NavigationRoutes.INBOX) {
                InboxScreen()
            }
            composable(NavigationRoutes.NOTIFICATIONS) {
                NotificationsScreen()
            }
            composable(NavigationRoutes.PROFILE) {
                ProfileScreen()
            }
        }
    }
}