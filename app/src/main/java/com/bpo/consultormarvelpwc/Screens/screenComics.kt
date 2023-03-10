package com.bpo.consultormarvelpwc.NavConfig

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.bpo.consultormarvelpwc.MainActivity.Companion.listaComics
import com.bpo.consultormarvelpwc.MyGrid

@Composable
//Imprime laa cuadrículas de comics con las cards
fun ScreenComics(navController: NavHostController){
    MyGrid(listaComics, navController)
}