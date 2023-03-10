package com.bpo.consultormarvelpwc.NavConfig

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bpo.consultormarvelpwc.Screens.ScreenDetails

@Composable
fun NavigationHost(navController: NavHostController, MyGrid: @Composable (() -> Unit)? = null){
    NavHost(navController = navController,
        startDestination = ItemsNav.ScreenPersonajes.route
    ){
        composable (ItemsNav.ScreenPersonajes.route){
            ScreenPersonajes(navController)
        }
        composable (ItemsNav.ScreenComics.route){
            ScreenComics(navController)
        }
        composable (ItemsNav.ScreenSeries.route){
            ScreenSeries(navController)
        }
        composable (ItemsNav.ScreenDetalles.route + "/{name}/{description}/{thumbnail}"){
            backStackEntry ->
            val name = backStackEntry.arguments?.getString("name")
            val description = backStackEntry.arguments?.getString("description")
            val thumbnail = backStackEntry.arguments?.getString("thumbnail")
            requireNotNull(name)
            requireNotNull(description)
            requireNotNull(thumbnail)

            ScreenDetails(navController, name, description, thumbnail)
        }
    }
}