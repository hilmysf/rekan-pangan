package id.hilmysf.rekanpanganpi.ui.screens

import BottomBar
import OrderScreen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.hilmysf.rekanpanganpi.navigation.Screen

@Composable
fun HomeScreen(rootNavController: NavController) {
    val bottomNavController = rememberNavController()
    Scaffold(
        bottomBar = { BottomBar(navController = bottomNavController) }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(PaddingValues(bottom = innerPadding.calculateBottomPadding()))) {
            NavHost(
                navController = bottomNavController,
                startDestination = Screen.dashboard.route,
            ) {
                composable(Screen.dashboard.route) {
                    DashboardScreen(onCartClick = {
                        rootNavController.navigate(Screen.cart.route)
                    },
                        onLogout = {
                            rootNavController.navigate(Screen.login.route) {
                                popUpTo(0) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                            }
                        }
                    )
                }
                composable(Screen.order.route) {
                    OrderScreen()
                }
            }
        }
    }
}