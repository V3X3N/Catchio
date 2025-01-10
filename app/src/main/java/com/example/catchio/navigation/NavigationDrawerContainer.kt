package com.example.catchio.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawerContent(
    navController: NavHostController,
    drawerState: DrawerState,
    scope: CoroutineScope,
    currentDestination: NavDestination?
) {
    ModalDrawerSheet(
        Modifier
            .width(300.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NavigationDrawerItem(
                label = { Text("Main") },
                selected = currentDestination?.route == Screen.Main.route,
                onClick = {
                    scope.launch { drawerState.close() }
                    navController.navigateSingleTopTo(Screen.Main.route)
                },
                modifier = Modifier.padding(vertical = 8.dp)
            )
            NavigationDrawerItem(
                label = { Text("Route") },
                selected = currentDestination?.route == Screen.Route.route,
                onClick = {
                    scope.launch { drawerState.close() }
                    navController.navigateSingleTopTo(Screen.Route.route)
                },
                modifier = Modifier.padding(vertical = 8.dp)
            )
            NavigationDrawerItem(
                label = { Text("Backpack") },
                selected = currentDestination?.route == Screen.Backpack.route,
                onClick = {
                    scope.launch { drawerState.close() }
                    navController.navigateSingleTopTo(Screen.Backpack.route)
                },
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}