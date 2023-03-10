package com.bpo.consultormarvelpwc.NavConfig

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.bpo.consultormarvelpwc.MainActivity
import com.bpo.consultormarvelpwc.MyGrid

@Composable
//Imprime laa cuadrículas de series con las cards
fun ScreenSeries(navController: NavHostController){
    MyGrid(MainActivity.listaSeries, navController)
}