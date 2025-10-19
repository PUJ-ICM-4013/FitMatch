package com.example.fitmatch.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fitmatch.presentation.ui.components.BottomNavItem
import com.example.fitmatch.presentation.ui.components.BottomNavigationBar
import com.example.fitmatch.presentation.ui.screens.auth.ui.LoginScreen
import com.example.fitmatch.presentation.ui.screens.auth.ui.RegisterScreen
import com.example.fitmatch.presentation.ui.screens.auth.ui.WelcomeScreen
import com.example.fitmatch.presentation.ui.screens.cliente.CartScreen
import com.example.fitmatch.presentation.ui.screens.cliente.ClienteDashboardScreen
import com.example.fitmatch.presentation.ui.screens.cliente.FavoritesScreen
import com.example.fitmatch.presentation.ui.screens.cliente.ProfileScreen
import com.example.fitmatch.presentation.ui.screens.common.ChatListScreen
import com.example.fitmatch.presentation.ui.screens.common.ChatScreen
import com.example.fitmatch.presentation.ui.screens.common.DeliveryPickupScreen
import com.example.fitmatch.presentation.ui.screens.common.NotificationsScreen
import com.example.fitmatch.presentation.ui.screens.common.OrdersScreen
import com.example.fitmatch.presentation.ui.screens.common.ProductDetailScreen
import com.example.fitmatch.presentation.ui.screens.common.SearchScreen
import com.example.fitmatch.presentation.ui.screens.common.StoreProfileScreen
import com.example.fitmatch.presentation.ui.screens.common.TitoChatScreen
// Si tienes pantallas de vendedor, impórtalas y úsalas en el if(role=="Vendedor")

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentRole = navBackStackEntry?.arguments?.getString("role")

    // Rutas donde se muestra la bottom bar
    val bottomBarRoutes = remember {
        setOf(
            AppScreens.Cart.route,
            AppScreens.Notifications.route,
            AppScreens.Profile.route,
            AppScreens.DeliveryPickup.route,
            AppScreens.Favorites.route,
            AppScreens.ChatList.route
        )
    }

    // Home que aparece en la bottom bar (según rol actual si existe; por defecto Cliente)
    val homeRouteForBar = if (currentRole.equals("Vendedor", ignoreCase = true)) {
        AppScreens.Home.withRole("Vendedor")
    } else {
        AppScreens.Home.withRole("Cliente")
    }

    val bottomNavItems = remember(homeRouteForBar) {
        listOf(
            BottomNavItem(
                route = homeRouteForBar,
                icon = Icons.Filled.Home,
                label = "Home"
            ),
            BottomNavItem(
                route = AppScreens.Cart.route,
                icon = Icons.Filled.ShoppingCart,
                label = "Cart"
            ),
            BottomNavItem(
                route = AppScreens.ChatList.route,
                icon = Icons.Filled.Email,
                label = "Chat_list"
            ),
            BottomNavItem(
                route = AppScreens.Notifications.route,
                icon = Icons.Filled.Notifications,
                label = "Notifications",
                badgeCount = 5
            ),
            BottomNavItem(
                route = AppScreens.Profile.route,
                icon = Icons.Filled.Person,
                label = "Profile",
                isProfile = true
            )
        )
    }

    Scaffold(
        bottomBar = {
            if (
                currentRoute in bottomBarRoutes ||
                currentRoute?.startsWith("home/") == true // cubre home/{role}
            ) {
                BottomNavigationBar(
                    items = bottomNavItems,
                    currentRoute = currentRoute ?: homeRouteForBar,
                    onItemClick = { route ->
                        if (currentRoute != route) {
                            navController.navigate(route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    profileImageUrl = null
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = AppScreens.Welcome.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            // Onboarding
            composable(AppScreens.Welcome.route) {
                WelcomeScreen(
                    onCreateAccount = { navController.navigate(AppScreens.Register.route) },
                    onContinueWithGoogle = { navController.navigate(AppScreens.Login.route) },
                    onContinueWithApple = { navController.navigate(AppScreens.Login.route) }
                )
            }

            composable(AppScreens.Login.route) {
                LoginScreen(
                    onLoginClick = { navController.navigate(AppScreens.Home.withRole("Cliente")) },
                    onBackClick = { navController.popBackStack() },
                    onForgotPasswordClick = {}
                )
            }

            composable(AppScreens.Register.route) {
                var role by remember { mutableStateOf("Cliente") }
                RegisterScreen(
                    onRoleClick = { role = it },
                    onRegisterClick = {
                        navController.navigate(AppScreens.Home.withRole(role)) {
                            popUpTo(AppScreens.Welcome.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onBackClick = { navController.popBackStack() }
                )
            }

            // Home con rol
            composable(
                route = AppScreens.Home.route,
                arguments = listOf(navArgument("role") { type = NavType.StringType })
            ) { backStackEntry ->
                val role = backStackEntry.arguments?.getString("role") ?: "Cliente"

                // Si tienes pantalla específica de Vendedor, úsala aquí
                if (role.equals("Vendedor", ignoreCase = true)) {
                    // TODO: Reemplaza por tu pantalla real de vendedor si existe
                    ClienteDashboardScreen(
                        onBackClick = { navController.popBackStack() },
                        onOpenStore = { navController.navigate(AppScreens.StoreProfile.route) },
                        onProductSeen = { navController.navigate(AppScreens.ProductDetail.route) },
                        onProductLiked = {},
                        onProductPassed = {},
                        onFilterClick = { navController.navigate(AppScreens.Search.route) }
                    )
                } else {
                    ClienteDashboardScreen(
                        onBackClick = { navController.popBackStack() },
                        onOpenStore = { navController.navigate(AppScreens.StoreProfile.route) },
                        onProductSeen = { navController.navigate(AppScreens.ProductDetail.route) },
                        onProductLiked = {},
                        onProductPassed = {},
                        onFilterClick = { navController.navigate(AppScreens.Search.route) }
                    )
                }
            }

            composable(AppScreens.Search.route) {
                SearchScreen(
                    onBackClick = { navController.popBackStack() },
                    onSearchClick = {
                        // Navega a donde prefieras tras buscar:
                        navController.navigate(AppScreens.ProductDetail.route)
                    }
                )
            }

            composable(AppScreens.ProductDetail.route) {
                ProductDetailScreen(
                    onBuyClick = { navController.navigate(AppScreens.Cart.route) },
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(AppScreens.Cart.route) {
                CartScreen(
                    onCheckoutClick = { navController.navigate(AppScreens.DeliveryPickup.route) },
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(AppScreens.Orders.route) {
                OrdersScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(AppScreens.DeliveryPickup.route) {
                DeliveryPickupScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }


            // Chat genérico (si tienes una pantalla sin id)
            composable(AppScreens.Chat.route) {
                ChatScreen(
                    onBackClick = { navController.popBackStack() },
                    onMoreClick = {},
                    onCallClick = {}
                )
            }

            // Chat list -> abre chat por id o Tito
            composable(AppScreens.ChatList.route) {
                ChatListScreen(
                    onOpenChat = { chatId, isTito ->
                        if (isTito) {
                            navController.navigate(AppScreens.TitoChat.route)
                        } else {
                            navController.navigate("chat/$chatId")
                        }
                    },
                    onBackClick = { navController.popBackStack() },
                    onNewChat = { /* ... */ }
                )
            }

            composable(AppScreens.TitoChat.route) {
                TitoChatScreen(
                    onBackClick = { navController.popBackStack() },
                    onViewProduct = { /* navController.navigate(AppScreens.ProductDetail.route) */ },
                    onViewProfile = { /* navController.navigate(AppScreens.StoreProfile.route) */ }
                )
            }

            composable(AppScreens.Favorites.route) {
                FavoritesScreen(
                    onBack = { navController.popBackStack() },
                    onCartClick = {},
                    onAddCategory = {},
                    onOpenProduct = {}
                )
            }

            composable(AppScreens.Notifications.route) {
                NotificationsScreen(
                    onNotificationClick = {},
                    onMarkAllRead = {}
                )
            }

            composable(AppScreens.Profile.route) {
                ProfileScreen(
                    onBackClick = { navController.popBackStack() },
                    onSavedClick = { navController.navigate(AppScreens.Favorites.route) },
                    onLogoutClick = {
                        navController.navigate(AppScreens.Welcome.route) {
                            popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(AppScreens.StoreProfile.route) {
                StoreProfileScreen(
                    onBackClick = { navController.popBackStack() },
                    onFollowClick = {},
                    onProductClick = {}
                )
            }
        }
    }
}
