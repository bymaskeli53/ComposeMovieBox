package com.example.composemoviebox.ui.common

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val items = listOf(Screen.MoviesScreen, Screen.FavoritesScreen, Screen.ChatScreen)

    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painterResource(id = screen.icon!!),
                        contentDescription = null,
                    )
                },
                label = { Text(screen.route) },
                selected = navController.currentDestination?.route == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}
