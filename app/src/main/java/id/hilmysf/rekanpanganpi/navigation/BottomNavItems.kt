package id.hilmysf.rekanpanganpi.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import id.hilmysf.rekanpanganpi.R

sealed class BottomNavItem(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int
) {
    data object Dashboard : BottomNavItem("dashboard", R.string.dashboard, R.drawable.ic_dashboard)
    data object Order : BottomNavItem("order", R.string.order, R.drawable.ic_order)
}