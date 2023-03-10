package com.bpo.consultormarvelpwc.NavConfig

import com.bpo.consultormarvelpwc.R

sealed class ItemsNav(
    val icon: Int,
    val title: String,
    val route: String
) {
    object ScreenPersonajes : ItemsNav(
        R.drawable.ic_personajes,
        "Personajes",
        "screenPersonajes"
    )

    object ScreenComics : ItemsNav(
        R.drawable.ic_comic,
        "Comics",
        "screenComics"
    )

    object ScreenSeries : ItemsNav(
        R.drawable.ic_serie,
        "Series",
        "screenSeries"
    )

    object ScreenDetalles : ItemsNav(
        R.drawable.ic_personajes,
        "Detalle",
        "screenDetalles"
    )
}
