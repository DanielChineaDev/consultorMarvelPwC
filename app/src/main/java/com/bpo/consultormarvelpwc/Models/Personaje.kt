package com.bpo.consultormarvelpwc

import com.google.gson.annotations.SerializedName

data class Personaje(
    @SerializedName("name") override var name: String,
    @SerializedName("description") override var description: String,
    @SerializedName("thumbnail") override var thumbnail: String
): Contenido

data class Thumbnail(
    @SerializedName("path") var path: String? = null,
    @SerializedName("extension") var extension: String? = null
) {
    fun getUrl(): String {
        return "$path.$extension"
    }
}