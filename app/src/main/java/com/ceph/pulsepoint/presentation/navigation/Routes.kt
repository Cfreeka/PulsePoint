package com.ceph.pulsepoint.presentation.navigation


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Routes(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector?,
    val unselectedIcon: ImageVector?
) {

    data object Home : Routes(
        title = "Home",
        route = "home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Filled.Home
    )

    data object Profile : Routes(
        title = "Profile",
        route = "profile",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Filled.Person
    )

    data object Favorite : Routes(
        title = "Favorite",
        route = "favorite",
        selectedIcon = Icons.Filled.Favorite,
        unselectedIcon = Icons.Outlined.FavoriteBorder
    )
    data object Search :Routes(
        title = "Search",
        route = "search",
        selectedIcon = Icons.Filled.Search,
        unselectedIcon = Icons.Outlined.Search
    )

    data object Login:Routes(
        title = "Login",
        route = "login",
        selectedIcon = null,
        unselectedIcon = null
    )

}