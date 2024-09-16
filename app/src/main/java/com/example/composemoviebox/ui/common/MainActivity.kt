package com.example.composemoviebox.ui.common

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bottomNavItems = listOf(
            Screen.MoviesScreen,
            Screen.FavoritesScreen
        )
        setContent {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = {
                    val currentDestionation =
                        navController.currentBackStackEntryAsState().value?.destination

                    if (currentDestionation?.route in bottomNavItems.map { it.route }){
                        BottomNavigationBar(navController = navController)
                    }
                },
            ) { innerPadding ->
                Navigation(navController = navController, modifier = Modifier.padding(innerPadding))
            }
            // val viewModel: MovieViewModel = hiltViewModel()

            // MoviesScreen()
        }
    }
}
