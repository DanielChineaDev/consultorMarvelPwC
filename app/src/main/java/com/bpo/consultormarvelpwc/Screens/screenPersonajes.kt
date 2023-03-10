package com.bpo.consultormarvelpwc.NavConfig

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.bpo.consultormarvelpwc.MainActivity.Companion.listaPersonajes
import com.bpo.consultormarvelpwc.MyGrid

@Composable
//Imprime laa cuadrículas de personajes con las cards
fun ScreenPersonajes(navController: NavHostController) {
    MyGrid(listaPersonajes, navController)
}