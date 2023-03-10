package com.bpo.consultormarvelpwc.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.bpo.consultormarvelpwc.R

@Composable
//Pantalla principal de los detalles
fun ScreenDetails(navController: NavController, name: String, description: String, thumbnail: String) {
    Box(
        modifier = Modifier.fillMaxSize().scrollable(
            rememberScrollState(),
            orientation = Orientation.Vertical
        )
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            MyImage(thumbnail)
            MyTextDetails(name, description)
        }
    }
}

@Composable
//Texto del modo detalle
fun MyTextDetails(name: String, description: String){
    Text(
        text = name,
        fontSize = 26.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 8.dp)
    )
    Text(
        text = "Información:\n\n" + description,
        fontSize = 18.sp,
        textAlign = TextAlign.Justify,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
//Imagen con un placeholder para mostrar antes de que se cargue la foto de la URL o por si no existe, además, la imagen se ajusta al espacio para no dejar huecos blancos
fun MyImage(imageUrl: String) {
    Image(
        painter = rememberImagePainter(
            data = imageUrl,
            builder = {
                crossfade(true)
                placeholder(R.mipmap.ic_placeholder_1)
            }
        ),
        contentDescription = "Foto que se usa de carátula",
        modifier = Modifier
            .height(400.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp)),
        contentScale = ContentScale.Crop,
    )
}