package id.hilmysf.rekanpanganpi.navigation

sealed class Screen(val route: String) {
    object login : Screen("login")
    object register : Screen("register")
    object home : Screen("home")
    object dashboard : Screen("dashboard")
    object order : Screen("order")
    object cart : Screen("cart")
    object map : Screen("map")
    object checkout : Screen("checkout")
}