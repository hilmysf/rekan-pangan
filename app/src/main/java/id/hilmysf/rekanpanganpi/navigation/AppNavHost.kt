package id.hilmysf.rekanpanganpi.navigation

import LoginScreen
import OrderScreen
import RegisterScreen
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.hilmysf.rekanpanganpi.ui.screens.CartScreen
import id.hilmysf.rekanpanganpi.ui.screens.CheckoutScreen
import id.hilmysf.rekanpanganpi.ui.screens.HomeScreen
import id.hilmysf.rekanpanganpi.ui.screens.MapScreen
import id.hilmysf.rekanpanganpi.viewmodel.AuthViewModel

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = hiltViewModel()
    val currentUser = authViewModel.getCurrentUser()
    Log.d(TAG, "currentUser: $currentUser")
    val initialRoute = if (currentUser == null) {
        Screen.login.route
    } else {
        Screen.home.route
    }

    NavHost(
        navController = navController,
        startDestination = initialRoute
    ) {
        composable(Screen.login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.home.route) {
                        popUpTo(Screen.login.route) { inclusive = true }
                    }
                },
                onCreateAccount = {
                    navController.navigate(Screen.register.route)
                }
            )
        }
        composable(Screen.register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screen.home.route) {
                        popUpTo(Screen.register.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.home.route) {
            HomeScreen(navController)
        }

        composable(Screen.cart.route) {
            CartScreen(
                navController,
                onOpenMapClick = {
                    navController.navigate(Screen.map.route)
                },
                onCheckoutClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.map.route) {
            MapScreen(
                onLocationPicked = { address, lat, lng ->
                    Log.d(TAG, "onLocationPicked: $address, $lat, $lng")
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("selected_address", address)
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("selected_lat", lat)
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("selected_lng", lng)
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.checkout.route) {
            CheckoutScreen()
        }

        composable(Screen.order.route) {
            OrderScreen()
        }
    }
}
