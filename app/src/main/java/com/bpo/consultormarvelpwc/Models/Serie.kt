package com.bpo.consultormarvelpwc.Models

import com.bpo.consultormarvelpwc.Contenido
import com.google.gson.annotations.SerializedName

//Clase Serie
data class Serie(
    @SerializedName("title") override var name: String,
    @SerializedName("description") override var description: String,
    @SerializedName("thumbnail") override var thumbnail: String
): Contenido

