package com.vcoffee.catchio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vcoffee.catchio.navigation.NavigationDrawerContent
import com.vcoffee.catchio.navigation.Screen
import com.vcoffee.catchio.screens.MainScreen
import com.vcoffee.catchio.screens.route.Screen1
import com.vcoffee.catchio.screens.backpack.BackpackScreen
import com.vcoffee.catchio.screens.login.LoginScreen
import com.vcoffee.catchio.screens.town.TownDetailsScreen
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        setContent {
            val navController = rememberNavController()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            val isLoggedIn = remember { mutableStateOf(false) }

            if (!isLoggedIn.value) {
                LoginScreen(onLoginSuccess = {
                    isLoggedIn.value = true
                })
            } else {
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    gesturesEnabled = true,
                    drawerContent = {
                        NavigationDrawerContent(
                            navController = navController,
                            drawerState = drawerState,
                            scope = scope,
                            currentDestination = currentDestination
                        )
                    }
                ) {
                    Scaffold(
                        topBar = {
                            CenterAlignedTopAppBar(
                                title = { Text(currentDestination?.route ?: "Main") },
                                navigationIcon = {
                                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                                    }
                                }
                            )
                        },
                        modifier = Modifier.padding()
                    ) { padding ->
                        NavHost(
                            navController = navController,
                            startDestination = Screen.Main.route,
                            Modifier.padding(padding)
                        ) {
                            composable(Screen.Main.route) { MainScreen() }
                            composable(Screen.Route.route) { Screen1(navController) }
                            composable(Screen.Backpack.route) { BackpackScreen() }
                            composable(
                                "townDetails/{row}/{column}",
                                arguments = listOf(
                                    navArgument("row") { type = NavType.IntType },
                                    navArgument("column") { type = NavType.IntType }
                                )
                            ) { backStackEntry ->
                                val row = backStackEntry.arguments?.getInt("row") ?: 0
                                val column = backStackEntry.arguments?.getInt("column") ?: 0
                                TownDetailsScreen(row, column)
                            }
                        }
                    }
                }
            }
        }
    }
}